package com.lg.theex.domain.coffee.service;

import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeListRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeePopularRecipeListRequest;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListItemResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeePopularRecipeListResponse;
import com.lg.theex.domain.coffee.repository.CoffeeRecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoffeeRecipeService {

    private final CoffeeRecipeRepository coffeeRecipeRepository;

    public CoffeeRecipeListResponse getBasicRecipeList() {
        return getBasicRecipeList(CoffeeRecipeListRequest.builder().build());
    }

    public CoffeeRecipeListResponse getBasicRecipeList(CoffeeRecipeListRequest request) {
        List<CoffeeRecipeListItemResponse> recipes = coffeeRecipeRepository
                .findWithDetailsByUserUserIdOrderByRecipeIdDesc(request.getUserId())
                .stream()
                .map(CoffeeRecipeListItemResponse::from)
                .toList();

        return CoffeeRecipeListResponse.builder()
                .userId(request.getUserId())
                .totalCount(recipes.size())
                .recipes(recipes)
                .build();
    }

    public CoffeePopularRecipeListResponse getPopularRecipeList() {
        return getPopularRecipeList(CoffeePopularRecipeListRequest.builder().build());
    }

    public CoffeePopularRecipeListResponse getPopularRecipeList(CoffeePopularRecipeListRequest request) {
        List<CoffeeRecipeListItemResponse> recipes = coffeeRecipeRepository
                .findWithDetailsByUserUserIdNotAndIsSharedTrueOrderBySaveCountDescRecipeIdDesc(request.getExcludedUserId())
                .stream()
                .map(CoffeeRecipeListItemResponse::from)
                .toList();

        return CoffeePopularRecipeListResponse.builder()
                .excludedUserId(request.getExcludedUserId())
                .totalCount(recipes.size())
                .recipes(recipes)
                .build();
    }
}
