package com.lg.theex.domain.mood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.theex.domain.mood.dto.response.CurrentMoodResponseDTO;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.InternalServerErrorException;
import com.lg.theex.global.serial.SerialTransferClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentMoodSerialService {

    private final CurrentMoodService currentMoodService;
    private final SerialTransferClient serialTransferClient;
    private final ObjectMapper objectMapper;

    public String sendCurrentMoodToEsp32() {
        CurrentMoodResponseDTO currentMood = currentMoodService.getCurrentMood();

        final String payload;
        try {
            payload = objectMapper.writeValueAsString(currentMood) + "\n";
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to serialize current mood.");
        }

        serialTransferClient.sendLine(payload);
        return payload;
    }
}
