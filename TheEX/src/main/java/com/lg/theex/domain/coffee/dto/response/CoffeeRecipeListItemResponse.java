package com.lg.theex.domain.coffee.dto.response;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.CapsuleTemp;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeRecipeListItemResponse {

    private Long recipeId;
    private String recipeName;
    private RecipeCategory recipeCategory;
    private Long userId;
    private String userNickname;
    private Long capsule1Id;
    private String capsule1Name;
    private Long capsule2Id;
    private String capsule2Name;
    private CapsuleTemp capsuleTemp1;
    private CapsuleTemp capsuleTemp2;
    private Integer capsule1Size;
    private Integer capsule2Size;
    private Integer capsule1Step1;
    private Integer capsule2Step2;
    private Integer capsule1Step3;
    private Integer capsule2Step4;
    private String addObj;
    private String recipeMemo;
    private Boolean isExtract;
    private Long originRecipeId;
    private RecipeLevel recipeLevel;
    private Boolean isShared;
    private Integer saveCount;

    public static CoffeeRecipeListItemResponse from(CoffeeRecipeEntity entity) {
        return CoffeeRecipeListItemResponse.builder()
                .recipeId(entity.getRecipeId())
                .recipeName(entity.getRecipeName())
                .recipeCategory(entity.getRecipeCategory())
                .userId(entity.getUser().getUserId())
                .userNickname(entity.getUser().getUserNickname())
                .capsule1Id(entity.getCapsule1() != null ? entity.getCapsule1().getCapsuleId() : null)
                .capsule1Name(entity.getCapsule1() != null ? entity.getCapsule1().getCapsuleName() : null)
                .capsule2Id(entity.getCapsule2() != null ? entity.getCapsule2().getCapsuleId() : null)
                .capsule2Name(entity.getCapsule2() != null ? entity.getCapsule2().getCapsuleName() : null)
                .capsuleTemp1(entity.getCapsuleTemp1())
                .capsuleTemp2(entity.getCapsuleTemp2())
                .capsule1Size(entity.getCapsule1Size())
                .capsule2Size(entity.getCapsule2Size())
                .capsule1Step1(entity.getCapsule1Step1())
                .capsule2Step2(entity.getCapsule2Step2())
                .capsule1Step3(entity.getCapsule1Step3())
                .capsule2Step4(entity.getCapsule2Step4())
                .addObj(entity.getAddObj())
                .recipeMemo(entity.getRecipeMemo())
                .isExtract(entity.getIsExtract())
                .originRecipeId(entity.getOriginRecipe() != null ? entity.getOriginRecipe().getRecipeId() : null)
                .recipeLevel(entity.getRecipeLevel())
                .isShared(entity.getIsShared())
                .saveCount(entity.getSaveCount())
                .build();
    }
}
