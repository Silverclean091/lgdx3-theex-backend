package com.lg.theex.global.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.ServiceUnavailableException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class SerialTransferClient {

    private final SerialProperties serialProperties;

    private SerialPort serialPort;

    @PostConstruct
    public void initialize() {
        if (!serialProperties.isEnabled()) {
            log.info("Serial is disabled by configuration.");
            return;
        }
        try {
            openPort();
        } catch (Exception e) {
            log.error("Serial initialization skipped due to error: {}", e.getMessage());
            serialPort = null;
        }
    }

    private void openPort() {
        serialPort = SerialPort.getCommPort(serialProperties.getPort());
        serialPort.setComPortParameters(serialProperties.getBaudRate(), 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, serialProperties.getWriteTimeoutMs());

        if (!serialPort.openPort()) {
            String availablePorts = Arrays.stream(SerialPort.getCommPorts())
                    .map(SerialPort::getSystemPortName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("(none)");

            String message = "Failed to open serial port: " + serialProperties.getPort()
                    + " | available ports: " + availablePorts;

            if (serialProperties.isFailFast()) {
                throw new ServiceUnavailableException(ErrorCode.SERVICE_UNAVAILABLE, message);
            }

            log.warn(message);
            serialPort = null;
            return;
        }
        log.info("Serial port opened: {} @ {}", serialProperties.getPort(), serialProperties.getBaudRate());
    }

    public synchronized void sendLine(String payload) {
        if (!serialProperties.isEnabled()) {
            throw new ServiceUnavailableException(
                    ErrorCode.SERVICE_UNAVAILABLE,
                    "Serial is disabled by configuration."
            );
        }

        if (serialPort == null || !serialPort.isOpen()) {
            openPort();
        }

        if (serialPort == null || !serialPort.isOpen()) {
            throw new ServiceUnavailableException(
                    ErrorCode.SERVICE_UNAVAILABLE,
                    "Serial port is not open."
            );
        }

        byte[] bytes = payload.getBytes(StandardCharsets.UTF_8);
        int written = serialPort.writeBytes(bytes, bytes.length);
        if (written != bytes.length) {
            log.warn("Serial write failed, retrying...");
            serialPort.closePort();
            openPort();

            if (serialPort == null || !serialPort.isOpen()) {
                throw new ServiceUnavailableException(
                        ErrorCode.SERVICE_UNAVAILABLE,
                        "Serial port reconnection failed."
                );
            }

            written = serialPort.writeBytes(bytes, bytes.length);
        }

        if (written != bytes.length) {
            throw new ServiceUnavailableException(
                ErrorCode.SERVICE_UNAVAILABLE,
                "Serial write failed."
            );
        }
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    @PreDestroy
    public void destroy() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
    }
}
