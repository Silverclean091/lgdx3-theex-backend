package com.lg.theex.domain.mood.entity.enumtype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;

import java.util.Arrays;

public enum LightColor {
    WARM_WHITE("Warm White"),
    SOFT_WHITE("Soft White"),
    DAYLIGHT("Daylight"),
    AMBER("Amber"),
    ORANGE("Orange"),
    BLUE("Blue"),
    PURPLE("Purple");

    private final String value;

    LightColor(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static LightColor from(String value) {
        return Arrays.stream(values())
                .filter(lightColor -> lightColor.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        ErrorCode.INVALID_FORMAT,
                        "지원하지 않는 lightColor 값입니다."
                ));
    }
}
