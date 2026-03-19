package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeRecipeShareToggleResponse {

    private Long recipeId;
    private String recipeType;
    private Boolean isShared;

    public static CoffeeRecipeShareToggleResponse from(CoffeeRecipeEntity entity) {
        return CoffeeRecipeShareToggleResponse.builder()
                .recipeId(entity.getRecipeId())
                .recipeType("COFFEE")
                .isShared(entity.getIsShared())
                .build();
    }

    public static CoffeeRecipeShareToggleResponse from(NoneCoffeeRecipeEntity entity) {
        return CoffeeRecipeShareToggleResponse.builder()
                .recipeId(entity.getRecipeId())
                .recipeType("NONE_COFFEE")
                .isShared(entity.getIsShared())
                .build();
    }
}
