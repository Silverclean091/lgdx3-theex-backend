package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeePopularRecipeListItemResponse {

    private Long recipeId;
    private String userNickname;
    private String recipeName;
    private Integer saveCount;

    public static CoffeePopularRecipeListItemResponse from(CoffeeRecipeEntity entity) {
        return CoffeePopularRecipeListItemResponse.builder()
                .recipeId(entity.getRecipeId())
                .userNickname(entity.getUser().getUserNickname())
                .recipeName(entity.getRecipeName())
                .saveCount(entity.getSaveCount())
                .build();
    }

    public static CoffeePopularRecipeListItemResponse from(NoneCoffeeRecipeEntity entity) {
        return CoffeePopularRecipeListItemResponse.builder()
                .recipeId(entity.getRecipeId())
                .userNickname(entity.getUser().getUserNickname())
                .recipeName(entity.getRecipeName())
                .saveCount(entity.getSaveCount())
                .build();
    }
}
