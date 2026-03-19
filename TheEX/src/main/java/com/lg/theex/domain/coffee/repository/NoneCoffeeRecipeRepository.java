package com.lg.theex.domain.coffee.repository;

import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoneCoffeeRecipeRepository extends JpaRepository<NoneCoffeeRecipeEntity, Long> {

    @Override
    boolean existsById(Long recipeId);

    @Override
    Optional<NoneCoffeeRecipeEntity> findById(Long recipeId);

    @Override
    <S extends NoneCoffeeRecipeEntity> S save(S entity);

    @EntityGraph(attributePaths = {"user", "originRecipe"})
    Optional<NoneCoffeeRecipeEntity> findWithDetailsByRecipeId(Long recipeId);

    @EntityGraph(attributePaths = {"user", "originRecipe"})
    List<NoneCoffeeRecipeEntity> findAllByUserUserIdOrderByRecipeIdDesc(Long userId);

    List<NoneCoffeeRecipeEntity> findAllByUserUserIdAndRecipeCategoryOrderByRecipeIdDesc(Long userId, RecipeCategory recipeCategory);

    @EntityGraph(attributePaths = {"user", "originRecipe"})
    List<NoneCoffeeRecipeEntity> findAllByIsSharedTrueOrderBySaveCountDescRecipeIdDesc();

    List<NoneCoffeeRecipeEntity> findAllByIsSharedTrueAndRecipeCategoryOrderBySaveCountDescRecipeIdDesc(RecipeCategory recipeCategory);

    List<NoneCoffeeRecipeEntity> findAllByRecipeLevelOrderByRecipeIdDesc(RecipeLevel recipeLevel);

    List<NoneCoffeeRecipeEntity> findAllByOriginRecipeRecipeId(Long originRecipeId);
}
