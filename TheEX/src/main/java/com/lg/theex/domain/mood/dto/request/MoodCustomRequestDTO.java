package com.lg.theex.domain.mood.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MoodCustomRequestDTO(
        @NotNull(message = "색상셋 ID는 필수입니다.")
        Long colorsetId,

        @NotBlank(message = "무드 이름은 필수입니다.")
        String moodName,

        String moodMemo,

        @Valid
        @NotNull(message = "커스텀 제품 정보는 필수입니다.")
        MoodCustomProductRequestDTO customProduct
) {
}
