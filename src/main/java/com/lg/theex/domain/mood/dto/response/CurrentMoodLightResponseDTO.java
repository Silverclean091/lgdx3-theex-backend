package com.lg.theex.domain.mood.dto.response;

public record CurrentMoodLightResponseDTO(
        Integer brightness,
        int[] color
) {
}
