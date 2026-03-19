package com.lg.theex.domain.coffee.dto.request;

import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CoffeeRecipeCustomizeNoneCoffeeRequest {

    private String recipeName;
    private RecipeCategory recipeCategory;
    private String ingredient;
    private String recipeContent;
    private Integer totalSize;
    private RecipeLevel recipeLevel;
}
