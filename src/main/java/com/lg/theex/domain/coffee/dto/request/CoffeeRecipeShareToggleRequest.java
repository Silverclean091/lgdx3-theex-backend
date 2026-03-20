package com.lg.theex.domain.coffee.dto.request;

import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CoffeeRecipeShareToggleRequest {

    private Long recipeId;
    private RecipeCategory recipeCategory;
}
