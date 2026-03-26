package com.lg.theex.domain.coffee.entity;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "none_coffee_recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoneCoffeeRecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersInfoEntity user;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_category", nullable = false)
    private RecipeCategory recipeCategory;

    @Column(name = "ingredient")
    private String ingredient;

    @Column(name = "recipe_content", nullable = false)
    private String recipeContent;

    @Column(name = "total_size")
    private Integer totalSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_recipe_id")
    private NoneCoffeeRecipeEntity originRecipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_level", nullable = false)
    private RecipeLevel recipeLevel;

    @Column(name = "is_shared", nullable = false)
    private Boolean isShared = false;

    @Column(name = "save_count", nullable = false)
    private Integer saveCount = 0;

    @Builder
    private NoneCoffeeRecipeEntity(
            UsersInfoEntity user,
            String recipeName,
            RecipeCategory recipeCategory,
            String ingredient,
            String recipeContent,
            Integer totalSize,
            NoneCoffeeRecipeEntity originRecipe,
            RecipeLevel recipeLevel,
            Boolean isShared,
            Integer saveCount
    ) {
        this.user = user;
        this.recipeName = recipeName;
        this.recipeCategory = recipeCategory;
        this.ingredient = ingredient;
        this.recipeContent = recipeContent;
        this.totalSize = totalSize;
        this.originRecipe = originRecipe;
        this.recipeLevel = recipeLevel;
        this.isShared = isShared;
        this.saveCount = saveCount;
    }

    public void toggleShared() {
        this.isShared = !this.isShared;
    }

    public void updateRecipe(
            String recipeName,
            RecipeCategory recipeCategory,
            String ingredient,
            String recipeContent,
            Integer totalSize,
            RecipeLevel recipeLevel
    ) {
        this.recipeName = recipeName;
        this.recipeCategory = recipeCategory;
        this.ingredient = ingredient;
        this.recipeContent = recipeContent;
        this.totalSize = totalSize;
        this.recipeLevel = recipeLevel;
    }

    public void assignOriginRecipeToSelf() {
        this.originRecipe = this;
    }
}
