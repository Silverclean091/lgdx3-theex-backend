package com.lg.theex.domain.mood.dto.response;

import com.lg.theex.domain.mood.entity.enumtype.MusicType;

public record SpeakerCustomDetailResponseDTO(
        String musicLink,
        Integer volume,
        MusicType musicType
) {
}
