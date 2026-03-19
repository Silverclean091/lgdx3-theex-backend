package com.lg.theex.domain.auth.dto.response;

public record MyRecipeListResponseDTO(
        Long recipeId,
        Boolean isCoffee,
        String recipeName
) {
}
