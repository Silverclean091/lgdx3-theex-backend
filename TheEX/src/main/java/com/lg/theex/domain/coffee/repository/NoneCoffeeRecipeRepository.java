package com.lg.theex.domain.coffee.repository;

import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoneCoffeeRecipeRepository extends JpaRepository<NoneCoffeeRecipeEntity, Long> {

    List<NoneCoffeeRecipeEntity> findAllByUserUserIdOrderByRecipeIdDesc(Long userId);

    List<NoneCoffeeRecipeEntity> findAllByUserUserIdAndRecipeCategoryOrderByRecipeIdDesc(Long userId, RecipeCategory recipeCategory);

    List<NoneCoffeeRecipeEntity> findAllByIsSharedTrueOrderBySaveCountDescRecipeIdDesc();

    List<NoneCoffeeRecipeEntity> findAllByIsSharedTrueAndRecipeCategoryOrderBySaveCountDescRecipeIdDesc(RecipeCategory recipeCategory);

    List<NoneCoffeeRecipeEntity> findAllByRecipeLevelOrderByRecipeIdDesc(RecipeLevel recipeLevel);

    List<NoneCoffeeRecipeEntity> findAllByOriginRecipeRecipeId(Long originRecipeId);
}
