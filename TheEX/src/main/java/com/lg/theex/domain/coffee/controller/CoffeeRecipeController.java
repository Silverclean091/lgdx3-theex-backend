package com.lg.theex.domain.coffee.controller;

import com.lg.theex.domain.coffee.dto.request.CoffeePopularRecipeListRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeListRequest;
import com.lg.theex.domain.coffee.dto.response.CoffeePopularRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListResponse;
import com.lg.theex.domain.coffee.service.CoffeeRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coffee/recipes")
public class CoffeeRecipeController {

    private final CoffeeRecipeService coffeeRecipeService;

    @GetMapping("/basic")
    public CoffeeRecipeListResponse getBasicRecipeList(
            @RequestParam(defaultValue = "1") Long userId
    ) {
        return coffeeRecipeService.getBasicRecipeList(
                CoffeeRecipeListRequest.builder()
                        .userId(userId)
                        .build()
        );
    }

    @GetMapping("/popular")
    public CoffeePopularRecipeListResponse getPopularRecipeList(
            @RequestParam(defaultValue = "1") Long excludedUserId
    ) {
        return coffeeRecipeService.getPopularRecipeList(
                CoffeePopularRecipeListRequest.builder()
                        .excludedUserId(excludedUserId)
                        .build()
        );
    }
}
