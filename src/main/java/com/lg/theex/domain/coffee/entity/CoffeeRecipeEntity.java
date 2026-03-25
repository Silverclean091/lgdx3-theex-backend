package com.lg.theex.domain.coffee.entity;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.coffee.entity.enumtype.CapsuleTemp;
import com.lg.theex.domain.coffee.entity.enumtype.CoffeeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeRecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_category", nullable = false)
    private RecipeCategory recipeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_category")
    private CoffeeCategory coffeeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersInfoEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_name1", nullable = false)
    private CoffeeCapsuleEntity capsule1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_name2")
    private CoffeeCapsuleEntity capsule2;

    @Enumerated(EnumType.STRING)
    @Column(name = "capsule_temp", nullable = false)
    private CapsuleTemp capsuleTemp;

    @Column(name = "capsule1_size", nullable = false)
    private Integer capsule1Size;

    @Column(name = "capsule2_size")
    private Integer capsule2Size;

    @Column(name = "capsule1_step1", nullable = false)
    private Integer capsule1Step1;

    @Column(name = "capsule2_step2")
    private Integer capsule2Step2;

    @Column(name = "capsule1_step3")
    private Integer capsule1Step3;

    @Column(name = "capsule2_step4")
    private Integer capsule2Step4;

    @Column(name = "add_obj")
    private String addObj;

    @Column(name = "recipe_memo")
    private String recipeMemo;

    @Column(name = "is_extract", nullable = false)
    private Boolean isExtract = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_recipe_id")
    private CoffeeRecipeEntity originRecipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_level", nullable = false)
    private RecipeLevel recipeLevel;

    @Column(name = "is_shared", nullable = false)
    private Boolean isShared = false;

    @Column(name = "save_count", nullable = false)
    private Integer saveCount = 0;

    @Builder
    private CoffeeRecipeEntity(
            Long recipeId,
            String recipeName,
            RecipeCategory recipeCategory,
            CoffeeCategory coffeeCategory,
            UsersInfoEntity user,
            CoffeeCapsuleEntity capsule1,
            CoffeeCapsuleEntity capsule2,
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
            CoffeeRecipeEntity originRecipe,
            RecipeLevel recipeLevel,
            Boolean isShared,
            Integer saveCount
    ) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeCategory = recipeCategory;
        this.coffeeCategory = coffeeCategory;
        this.user = user;
        this.capsule1 = capsule1;
        this.capsule2 = capsule2;
        this.capsuleTemp = capsuleTemp;
        this.capsule1Size = capsule1Size;
        this.capsule2Size = capsule2Size;
        this.capsule1Step1 = capsule1Step1;
        this.capsule2Step2 = capsule2Step2;
        this.capsule1Step3 = capsule1Step3;
        this.capsule2Step4 = capsule2Step4;
        this.addObj = addObj;
        this.recipeMemo = recipeMemo;
        this.isExtract = isExtract;
        this.originRecipe = originRecipe;
        this.recipeLevel = recipeLevel;
        this.isShared = isShared;
        this.saveCount = saveCount;
    }

    public void toggleShared() {
        this.isShared = !this.isShared;
    }

    public void assignOriginRecipeToSelf() {
        this.originRecipe = this;
    }
}
