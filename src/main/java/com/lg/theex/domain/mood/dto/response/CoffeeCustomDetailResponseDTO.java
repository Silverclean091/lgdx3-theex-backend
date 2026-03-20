package com.lg.theex.domain.mood.dto.response;

import com.lg.theex.domain.coffee.entity.enumtype.CapsuleTemp;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;

public record CoffeeCustomDetailResponseDTO(
        String recipeName,
        RecipeCategory recipeCategory,
        String capsule1Name,
        String capsule2Name,
        CapsuleTemp capsuleTemp,
        Integer capsule1Size,
        Integer capsule2Size,
        Integer capsule1Step1,
        Integer capsule2Step2,
        Integer capsule1Step3,
        Integer capsule2Step4,
        String addObj,
        String recipeMemo,
        Boolean isExtract,
        RecipeLevel recipeLevel,
        Boolean isShared,
        Integer saveCount
) {
}
