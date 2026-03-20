package com.lg.theex.domain.mood.dto.request;

import jakarta.validation.constraints.NotNull;

public record CoffeeCustomRequestDTO(
        @NotNull(message = "레시피 ID는 필수입니다.")
        Long recipeId
) {
}
