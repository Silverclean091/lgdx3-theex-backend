package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeRecipeCustomizeResponse {

    private Long recipeId;
    private Long userId;
    private String recipeType;
    private String recipeName;
    private RecipeCategory recipeCategory;
    private Boolean isExtract;
    private Boolean isShared;
    private Integer saveCount;

    public static CoffeeRecipeCustomizeResponse from(CoffeeRecipeEntity entity) {
        return CoffeeRecipeCustomizeResponse.builder()
                .recipeId(entity.getRecipeId())
                .userId(entity.getUser().getUserId())
                .recipeType("COFFEE")
                .recipeName(entity.getRecipeName())
                .recipeCategory(entity.getRecipeCategory())
                .isExtract(entity.getIsExtract())
                .isShared(entity.getIsShared())
                .saveCount(entity.getSaveCount())
                .build();
    }

    public static CoffeeRecipeCustomizeResponse from(NoneCoffeeRecipeEntity entity) {
        return CoffeeRecipeCustomizeResponse.builder()
                .recipeId(entity.getRecipeId())
                .userId(entity.getUser().getUserId())
                .recipeType("NONE_COFFEE")
                .recipeName(entity.getRecipeName())
                .recipeCategory(entity.getRecipeCategory())
                .isExtract(false)
                .isShared(entity.getIsShared())
                .saveCount(entity.getSaveCount())
                .build();
    }
}
