package com.lg.theex.domain.mood.entity.enumtype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;

import java.util.Arrays;

public enum MusicType {
    JAZZ("Jazz"),
    ACOUSTIC("Acoustic"),
    CLASSICAL("Classical"),
    CAFEBGM("Cafe BGM"),
    CHILL("Chill"),
    KPOP("K-POP"),
    MUSICAL("Musical");

    private final String value;

    MusicType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public int getTrackNumber() {
        return switch (this) {
            case CAFEBGM -> 1;
            case ACOUSTIC -> 2;
            case JAZZ -> 3;
            case CLASSICAL -> 4;
            case KPOP -> 5;
            case CHILL -> 6;
            case MUSICAL -> 7;
        };
    }

    public String getTrackNumberAsString() {
        return String.valueOf(getTrackNumber());
    }

    @JsonCreator
    public static MusicType from(String value) {
        return Arrays.stream(values())
                .filter(musicType -> musicType.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        ErrorCode.INVALID_FORMAT,
                        "지원하지 않는 musicType 값입니다."
                ));
    }
}
