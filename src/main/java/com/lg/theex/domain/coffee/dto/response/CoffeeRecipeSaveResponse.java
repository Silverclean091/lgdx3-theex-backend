package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.auth.entity.UserRecipeListEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeRecipeSaveResponse {

    private Long userRecipeId;
    private Long userId;
    private String recipeId;
    private Boolean isCoffee;

    public static CoffeeRecipeSaveResponse from(UserRecipeListEntity entity) {
        return CoffeeRecipeSaveResponse.builder()
                .userRecipeId(entity.getUserRecipeId())
                .userId(entity.getUser().getUserId())
                .recipeId(entity.getRecipeId())
                .isCoffee(entity.getIsCoffee())
                .build();
    }
}
