package com.lg.theex.domain.mood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.theex.domain.mood.dto.response.CurrentMoodResponseDTO;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.InternalServerErrorException;
import com.lg.theex.global.exception.exceptionType.ServiceUnavailableException;
import com.lg.theex.global.serial.SerialTransferClient;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentMoodSerialService {

    private final CurrentMoodService currentMoodService;
    private final SerialTransferClient serialTransferClient;
    private final ObjectMapper objectMapper;

    public String sendCurrentMoodToEsp32() {
        CurrentMoodResponseDTO currentMood;
        try {
            currentMood = currentMoodService.getCurrentMood();
        } catch (Exception e) {
            log.error("Failed to load current mood for serial send", e);
            throw e;
        }

        final String payload;
        try {
            payload = objectMapper.writeValueAsString(currentMood) + "\n";
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize current mood payload", e);
            throw new InternalServerErrorException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to serialize current mood.");
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            serialTransferClient.sendLine(payload);
        } catch (Exception e) {
            log.error("Serial transfer failed", e);
            throw new ServiceUnavailableException(ErrorCode.SERVICE_UNAVAILABLE, "Failed to send payload to serial device.");
        }
        return payload;
    }
}
