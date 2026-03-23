package com.lg.theex.domain.mood.dto.response;

public record CurrentMoodResponseDTO(
        CurrentMoodLightResponseDTO light,
        CurrentMoodSpeakerResponseDTO speaker
) {
}
