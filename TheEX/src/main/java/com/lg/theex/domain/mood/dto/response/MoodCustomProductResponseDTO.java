package com.lg.theex.domain.mood.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MoodCustomProductResponseDTO(
        LightCustomDetailResponseDTO lightCustom,
        SpeakerCustomDetailResponseDTO speakerCustom,
        CoffeeCustomDetailResponseDTO coffeeCustom
) {
}
