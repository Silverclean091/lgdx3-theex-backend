package com.lg.theex.domain.sensor.dto.response;

import java.time.LocalDateTime;

public record SensorLogResponseDTO(
        Float temperature,
        Float humidity,
        LocalDateTime recordedAt
) {
}
