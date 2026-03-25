package com.lg.theex.domain.coffee.repository;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.CoffeeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeLevel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRecipeRepository extends JpaRepository<CoffeeRecipeEntity, Long> {

    @Override
    boolean existsById(Long recipeId);

    @Override
    Optional<CoffeeRecipeEntity> findById(Long recipeId);

    @Override
    <S extends CoffeeRecipeEntity> S save(S entity);

    List<CoffeeRecipeEntity> findAllByUserUserIdOrderByRecipeIdDesc(Long userId);

    @EntityGraph(attributePaths = {"user", "capsule1", "capsule2", "originRecipe"})
    List<CoffeeRecipeEntity> findWithDetailsByUserUserIdOrderByRecipeIdDesc(Long userId);

    @EntityGraph(attributePaths = {"user", "capsule1", "capsule2", "originRecipe"})
    List<CoffeeRecipeEntity> findWithDetailsByUserUserIdNotAndIsSharedTrueOrderBySaveCountDescRecipeIdDesc(Long userId);

    @EntityGraph(attributePaths = {"user", "capsule1", "capsule2", "originRecipe"})
    Optional<CoffeeRecipeEntity> findWithDetailsByRecipeId(Long recipeId);

    List<CoffeeRecipeEntity> findAllByUserUserIdAndRecipeCategoryOrderByRecipeIdDesc(Long userId, RecipeCategory recipeCategory);

    List<CoffeeRecipeEntity> findAllByIsSharedTrueOrderBySaveCountDescRecipeIdDesc();

    List<CoffeeRecipeEntity> findAllByIsSharedTrueAndRecipeCategoryOrderBySaveCountDescRecipeIdDesc(RecipeCategory recipeCategory);

    List<CoffeeRecipeEntity> findAllByRecipeLevelOrderByRecipeIdDesc(RecipeLevel recipeLevel);

    List<CoffeeRecipeEntity> findAllByOriginRecipeRecipeId(Long originRecipeId);

    @EntityGraph(attributePaths = {"user", "capsule1", "capsule2", "originRecipe"})
    List<CoffeeRecipeEntity> findAllByCoffeeCategoryOrderBySaveCountDescRecipeIdDesc(CoffeeCategory coffeeCategory);
}
