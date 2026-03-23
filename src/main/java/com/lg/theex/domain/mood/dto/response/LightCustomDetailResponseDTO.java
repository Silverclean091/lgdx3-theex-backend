package com.lg.theex.domain.mood.dto.response;

import com.lg.theex.domain.mood.entity.enumtype.LightColor;

public record LightCustomDetailResponseDTO(
        LightColor lightColor,
        Integer lightBright
) {
}
