package com.lg.theex.domain.mood.dto.request;

import com.lg.theex.domain.mood.entity.enumtype.MusicType;

public record SpeakerCustomRequestDTO(
        String musicLink,
        Integer volume,
        MusicType musicType
) {
}
