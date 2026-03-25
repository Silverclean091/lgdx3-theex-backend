package com.lg.theex.global.serial;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.theex.domain.sensor.service.SensorLogService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
@Slf4j
public class SerialReaderClient {

    private final SerialTransferClient serialTransferClient;
    private final SensorLogService sensorLogService;
    private final ObjectMapper objectMapper;

    private Thread readerThread;
    private volatile boolean running;

    @PostConstruct
    public void start() {
        running = true;
        readerThread = new Thread(this::readLoop, "serial-reader");
        readerThread.setDaemon(true);
        readerThread.start();
        log.info("Serial reader started.");
    }

    private void readLoop() {
        while (running) {
            try {
                var port = serialTransferClient.getSerialPort();
                if (port == null || !port.isOpen()) {
                    Thread.sleep(1000);
                    continue;
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(port.getInputStream())
                );

                String line;
                while (running && (line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    try {
                        JsonNode node = objectMapper.readTree(line);
                        if ("sensor".equals(node.path("type").asText())) {
                            float temp = node.path("temp").floatValue();
                            float humidity = node.path("humidity").floatValue();
                            sensorLogService.save(temp, humidity);
                            log.info("Sensor saved: temp={}, humidity={}", temp, humidity);
                        }
                    } catch (Exception e) {
                        log.warn("Failed to parse serial line: {}", line);
                    }
                }
            } catch (Exception e) {
                if (e.getMessage() == null || !e.getMessage().contains("timed out")) {
                    log.error("Serial read error: {}", e.getMessage());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @PreDestroy
    public void stop() {
        running = false;
        if (readerThread != null) {
            readerThread.interrupt();
        }
    }
}
