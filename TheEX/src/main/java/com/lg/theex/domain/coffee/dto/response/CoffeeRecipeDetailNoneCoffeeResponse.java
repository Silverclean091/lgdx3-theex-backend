package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeRecipeDetailNoneCoffeeResponse implements CoffeeRecipeDetailResponse {

    private Long recipeId;
    private String recipeType;
    private Long userId;
    private String userNickname;
    private String recipeName;
    private RecipeCategory recipeCategory;
    private String ingredient;
    private String recipeContent;
    private Integer totalSize;
    private Long originRecipeId;
    private RecipeLevel recipeLevel;
    private Boolean isShared;
    private Integer saveCount;

    public static CoffeeRecipeDetailNoneCoffeeResponse from(NoneCoffeeRecipeEntity entity) {
        return CoffeeRecipeDetailNoneCoffeeResponse.builder()
                .recipeId(entity.getRecipeId())
                .recipeType("NONE_COFFEE")
                .userId(entity.getUser().getUserId())
                .userNickname(entity.getUser().getUserNickname())
                .recipeName(entity.getRecipeName())
                .recipeCategory(entity.getRecipeCategory())
                .ingredient(entity.getIngredient())
                .recipeContent(entity.getRecipeContent())
                .totalSize(entity.getTotalSize())
                .originRecipeId(entity.getOriginRecipe() != null ? entity.getOriginRecipe().getRecipeId() : null)
                .recipeLevel(entity.getRecipeLevel())
                .isShared(entity.getIsShared())
                .saveCount(entity.getSaveCount())
                .build();
    }
}
