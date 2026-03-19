package com.lg.theex.domain.coffee.service;

import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeDetailRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeListRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeePopularRecipeListRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeShareToggleRequest;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeDetailResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListItemResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeePopularRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeShareToggleResponse;
import com.lg.theex.domain.coffee.repository.CoffeeRecipeRepository;
import com.lg.theex.domain.coffee.repository.NoneCoffeeRecipeRepository;
import com.lg.theex.global.exception.CustomException;
import com.lg.theex.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoffeeRecipeService {

    private final CoffeeRecipeRepository coffeeRecipeRepository;
    private final NoneCoffeeRecipeRepository noneCoffeeRecipeRepository;

    @Transactional
    public CoffeeRecipeShareToggleResponse toggleRecipeShare(CoffeeRecipeShareToggleRequest request) {
        return coffeeRecipeRepository.findById(request.getRecipeId())
                .map(entity -> {
                    entity.toggleShared();
                    return CoffeeRecipeShareToggleResponse.from(entity);
                })
                .orElseGet(() -> noneCoffeeRecipeRepository.findById(request.getRecipeId())
                        .map(entity -> {
                            entity.toggleShared();
                            return CoffeeRecipeShareToggleResponse.from(entity);
                        })
                        .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST)));
    }

    public CoffeeRecipeDetailResponse getRecipeDetail(CoffeeRecipeDetailRequest request) {
        return coffeeRecipeRepository.findWithDetailsByRecipeId(request.getRecipeId())
                .map(CoffeeRecipeDetailResponse::from)
                .orElseGet(() -> noneCoffeeRecipeRepository.findWithDetailsByRecipeId(request.getRecipeId())
                        .map(CoffeeRecipeDetailResponse::from)
                        .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST)));
    }

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
