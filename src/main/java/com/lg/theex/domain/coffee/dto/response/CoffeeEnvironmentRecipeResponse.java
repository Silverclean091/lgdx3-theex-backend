package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeEnvironmentRecipeResponse {

    private Long recipeId;
    private String recipeName;

    public static CoffeeEnvironmentRecipeResponse from(CoffeeRecipeEntity recipe) {
        return CoffeeEnvironmentRecipeResponse.builder()
                .recipeId(recipe.getRecipeId())
                .recipeName(recipe.getRecipeName())
                .build();
    }
}
