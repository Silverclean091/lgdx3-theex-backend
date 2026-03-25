package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeEnvironmentRecipeResponse {

    private Long recipeId;
    private String recipeName;
    private RecipeCategory recipeCategory;

    public static CoffeeEnvironmentRecipeResponse from(CoffeeRecipeEntity recipe) {
        return CoffeeEnvironmentRecipeResponse.builder()
                .recipeId(recipe.getRecipeId())
                .recipeName(recipe.getRecipeName())
                .recipeCategory(recipe.getRecipeCategory())
                .build();
    }
}
