package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeBasicRecipeListItemResponse {

    private Long recipeId;
    private String recipeName;
    private RecipeCategory recipeCategory;
    private Integer saveCount;

    public static CoffeeBasicRecipeListItemResponse from(CoffeeRecipeEntity entity) {
        return CoffeeBasicRecipeListItemResponse.builder()
                .recipeId(entity.getRecipeId())
                .recipeName(entity.getRecipeName())
                .recipeCategory(entity.getRecipeCategory())
                .saveCount(entity.getSaveCount())
                .build();
    }

    public static CoffeeBasicRecipeListItemResponse from(NoneCoffeeRecipeEntity entity) {
        return CoffeeBasicRecipeListItemResponse.builder()
                .recipeId(entity.getRecipeId())
                .recipeName(entity.getRecipeName())
                .recipeCategory(entity.getRecipeCategory())
                .saveCount(entity.getSaveCount())
                .build();
    }
}
