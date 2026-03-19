package com.lg.theex.domain.coffee.dto.request;

import com.lg.theex.domain.coffee.entity.enumtype.CapsuleTemp;
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
public class CoffeeRecipeCustomizeCoffeeRequest {

    private String recipeName;
    private RecipeCategory recipeCategory;
    private Long capsule1Id;
    private Long capsule2Id;
    private CapsuleTemp capsuleTemp;
    private Integer capsule1Size;
    private Integer capsule2Size;
    private Integer capsule1Step1;
    private Integer capsule2Step2;
    private Integer capsule1Step3;
    private Integer capsule2Step4;
    private String addObj;
    private String recipeMemo;
    private RecipeLevel recipeLevel;
}
