package com.lg.theex.domain.mood.dto.request;

import com.lg.theex.domain.mood.entity.enumtype.LightColor;

public record LightCustomRequestDTO(
        LightColor lightColor,
        Integer lightBright
) {
}
