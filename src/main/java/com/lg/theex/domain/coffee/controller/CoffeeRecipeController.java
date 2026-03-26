package com.lg.theex.domain.coffee.controller;

import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeCustomizeCoffeeRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeCustomizeNoneCoffeeRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeDetailRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeSaveRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeShareToggleRequest;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeCustomizeResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeEnvironmentRecipeResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeDetailResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeePopularRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeSaveResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeShareToggleResponse;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.service.CoffeeRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coffee/recipes")
public class CoffeeRecipeController {

    private final CoffeeRecipeService coffeeRecipeService;

    @PostMapping("/save")
    public CoffeeRecipeSaveResponse saveRecipe(
            @RequestBody CoffeeRecipeSaveRequest request
    ) {
        return coffeeRecipeService.saveRecipe(request);
    }

    @DeleteMapping("/save")
    public CoffeeRecipeSaveResponse unsaveRecipe(
            @RequestParam Long recipeId,
            @RequestParam RecipeCategory recipeCategory
    ) {
        return coffeeRecipeService.unsaveRecipe(
                CoffeeRecipeSaveRequest.builder()
                        .recipeId(recipeId)
                        .recipeCategory(recipeCategory)
                        .build()
        );
    }

    @DeleteMapping("/{recipeId}")
    public void deleteOwnRecipe(
            @PathVariable Long recipeId,
            @RequestParam RecipeCategory recipeCategory
    ) {
        coffeeRecipeService.deleteOwnRecipe(recipeId, recipeCategory);
    }

    @PostMapping("/customize/coffee")
    public CoffeeRecipeCustomizeResponse customizeCoffeeRecipe(
            @RequestBody CoffeeRecipeCustomizeCoffeeRequest request
    ) {
        return coffeeRecipeService.customizeCoffeeRecipe(request);
    }

    @PatchMapping("/{recipeId}/customize/coffee")
    public CoffeeRecipeCustomizeResponse updateCoffeeRecipe(
            @PathVariable Long recipeId,
            @RequestBody CoffeeRecipeCustomizeCoffeeRequest request
    ) {
        return coffeeRecipeService.updateCoffeeRecipe(recipeId, request);
    }

    @PostMapping("/customize/none-coffee")
    public CoffeeRecipeCustomizeResponse customizeNoneCoffeeRecipe(
            @RequestBody CoffeeRecipeCustomizeNoneCoffeeRequest request
    ) {
        return coffeeRecipeService.customizeNoneCoffeeRecipe(request);
    }

    @PatchMapping("/{recipeId}/customize/none-coffee")
    public CoffeeRecipeCustomizeResponse updateNoneCoffeeRecipe(
            @PathVariable Long recipeId,
            @RequestBody CoffeeRecipeCustomizeNoneCoffeeRequest request
    ) {
        return coffeeRecipeService.updateNoneCoffeeRecipe(recipeId, request);
    }

    @PatchMapping("/{recipeId}/share")
    public CoffeeRecipeShareToggleResponse toggleRecipeShare(
            @PathVariable Long recipeId,
            @RequestParam RecipeCategory recipeCategory
    ) {
        return coffeeRecipeService.toggleRecipeShare(
                CoffeeRecipeShareToggleRequest.builder()
                        .recipeId(recipeId)
                        .recipeCategory(recipeCategory)
                        .build()
        );
    }

    @GetMapping("/{recipeId}")
    public CoffeeRecipeDetailResponse getRecipeDetail(
            @PathVariable Long recipeId,
            @RequestParam Boolean isCoffee
    ) {
        return coffeeRecipeService.getRecipeDetail(
                CoffeeRecipeDetailRequest.builder()
                        .recipeId(recipeId)
                        .isCoffee(isCoffee)
                        .build()
        );
    }

    @GetMapping("/basic")
    public CoffeeRecipeListResponse getBasicRecipeList() {
        return coffeeRecipeService.getBasicRecipeList();
    }

    @GetMapping("/popular")
    public CoffeePopularRecipeListResponse getPopularRecipeList() {
        return coffeeRecipeService.getPopularRecipeList();
    }

    @GetMapping("/ai-recommend")
    public CoffeeEnvironmentRecipeResponse getEnvironmentRecommendation() {
        return coffeeRecipeService.getEnvironmentRecommendation();
    }
}
