package com.lg.theex.domain.coffee.service;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.auth.entity.UserRecipeListEntity;
import com.lg.theex.domain.auth.repository.UserRecipeListRepository;
import com.lg.theex.domain.auth.repository.UsersInfoRepository;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeCustomizeCoffeeRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeCustomizeNoneCoffeeRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeDetailRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeListRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeSaveRequest;
import com.lg.theex.domain.coffee.dto.request.CoffeeRecipeShareToggleRequest;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeCustomizeResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeDetailCoffeeResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeDetailNoneCoffeeResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeDetailResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeBasicRecipeListItemResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeePopularRecipeListItemResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeePopularRecipeListResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeSaveResponse;
import com.lg.theex.domain.coffee.dto.response.CoffeeRecipeShareToggleResponse;
import com.lg.theex.domain.coffee.entity.CoffeeCapsuleEntity;
import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.enumtype.RecipeCategory;
import com.lg.theex.domain.coffee.repository.CoffeeRecipeRepository;
import com.lg.theex.domain.coffee.repository.CoffeeCapsuleRepository;
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

    private static final Long FIXED_USER_ID = 3L;
    private static final Long BASIC_RECIPE_USER_ID = 1L;
    private static final Long POPULAR_EXCLUDED_USER_ID = 1L;

    private final UsersInfoRepository usersInfoRepository;
    private final UserRecipeListRepository userRecipeListRepository;
    private final CoffeeCapsuleRepository coffeeCapsuleRepository;
    private final CoffeeRecipeRepository coffeeRecipeRepository;
    private final NoneCoffeeRecipeRepository noneCoffeeRecipeRepository;

    @Transactional
    public CoffeeRecipeSaveResponse saveRecipe(CoffeeRecipeSaveRequest request) {
        if (request.getRecipeId() == null || request.getRecipeCategory() == null) {
            throw new CustomException(ErrorCode.MISSING_REQUIRED_FIELD);
        }

        UsersInfoEntity user = usersInfoRepository.findById(FIXED_USER_ID)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));

        Boolean isCoffee = isCoffeeCategory(request.getRecipeCategory());
        validateRecipeExists(request.getRecipeId(), isCoffee);
        UserRecipeListEntity savedEntity = saveUserRecipeList(user, request.getRecipeId(), isCoffee);

        return CoffeeRecipeSaveResponse.from(savedEntity);
    }

    @Transactional
    public CoffeeRecipeCustomizeResponse customizeCoffeeRecipe(CoffeeRecipeCustomizeCoffeeRequest request) {
        UsersInfoEntity user = usersInfoRepository.findById(FIXED_USER_ID)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
        validateCoffeeCustomizeRequest(request);

        CoffeeCapsuleEntity capsule1 = coffeeCapsuleRepository.findById(request.getCapsule1Id())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
        CoffeeCapsuleEntity capsule2 = request.getCapsule2Id() == null
                ? null
                : coffeeCapsuleRepository.findById(request.getCapsule2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));

        CoffeeRecipeEntity entity = coffeeRecipeRepository.save(
                CoffeeRecipeEntity.builder()
                        .recipeName(request.getRecipeName())
                        .recipeCategory(request.getRecipeCategory())
                        .user(user)
                        .capsule1(capsule1)
                        .capsule2(capsule2)
                        .capsuleTemp(request.getCapsuleTemp())
                        .capsule1Size(request.getCapsule1Size())
                        .capsule2Size(request.getCapsule2Size())
                        .capsule1Step1(request.getCapsule1Step1())
                        .capsule2Step2(request.getCapsule2Step2())
                        .capsule1Step3(request.getCapsule1Step3())
                        .capsule2Step4(request.getCapsule2Step4())
                        .addObj(request.getAddObj())
                        .recipeMemo(request.getRecipeMemo())
                        .isExtract(true)
                        .originRecipe(null)
                        .recipeLevel(request.getRecipeLevel())
                        .isShared(false)
                        .saveCount(0)
                        .build()
        );
        entity.assignOriginRecipeToSelf();
        saveUserRecipeList(user, entity.getRecipeId(), true);

        return CoffeeRecipeCustomizeResponse.from(entity);
    }

    @Transactional
    public CoffeeRecipeCustomizeResponse customizeNoneCoffeeRecipe(CoffeeRecipeCustomizeNoneCoffeeRequest request) {
        UsersInfoEntity user = usersInfoRepository.findById(FIXED_USER_ID)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));

        validateNoneCoffeeCustomizeRequest(request);

        NoneCoffeeRecipeEntity entity = noneCoffeeRecipeRepository.save(
                NoneCoffeeRecipeEntity.builder()
                        .user(user)
                        .recipeName(request.getRecipeName())
                        .recipeCategory(request.getRecipeCategory())
                        .ingredient(request.getIngredient())
                        .recipeContent(request.getRecipeContent())
                        .totalSize(request.getTotalSize())
                        .originRecipe(null)
                        .recipeLevel(request.getRecipeLevel())
                        .isShared(false)
                        .saveCount(0)
                        .build()
        );
        entity.assignOriginRecipeToSelf();
        saveUserRecipeList(user, entity.getRecipeId(), false);

        return CoffeeRecipeCustomizeResponse.from(entity);
    }

    @Transactional
    public CoffeeRecipeShareToggleResponse toggleRecipeShare(CoffeeRecipeShareToggleRequest request) {
        if (request.getRecipeId() == null || request.getRecipeCategory() == null) {
            throw new CustomException(ErrorCode.MISSING_REQUIRED_FIELD);
        }

        if (request.getRecipeCategory() == RecipeCategory.COFFEE) {
            CoffeeRecipeEntity entity = coffeeRecipeRepository.findById(request.getRecipeId())
                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
            entity.toggleShared();
            return CoffeeRecipeShareToggleResponse.from(entity);
        }

        NoneCoffeeRecipeEntity entity = noneCoffeeRecipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
        entity.toggleShared();
        return CoffeeRecipeShareToggleResponse.from(entity);
    }

    public CoffeeRecipeDetailResponse getRecipeDetail(CoffeeRecipeDetailRequest request) {
        if (request.getRecipeId() == null || request.getIsCoffee() == null) {
            throw new CustomException(ErrorCode.MISSING_REQUIRED_FIELD);
        }

        if (Boolean.TRUE.equals(request.getIsCoffee())) {
            return coffeeRecipeRepository.findWithDetailsByRecipeId(request.getRecipeId())
                    .map(CoffeeRecipeDetailCoffeeResponse::from)
                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
        }

        return noneCoffeeRecipeRepository.findWithDetailsByRecipeId(request.getRecipeId())
                .map(CoffeeRecipeDetailNoneCoffeeResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
    }

    public CoffeeRecipeListResponse getBasicRecipeList() {
        return getBasicRecipeList(CoffeeRecipeListRequest.builder().build());
    }

    public CoffeeRecipeListResponse getBasicRecipeList(CoffeeRecipeListRequest request) {
        List<CoffeeBasicRecipeListItemResponse> coffeeRecipes = coffeeRecipeRepository
                .findWithDetailsByUserUserIdOrderByRecipeIdDesc(BASIC_RECIPE_USER_ID)
                .stream()
                .map(CoffeeBasicRecipeListItemResponse::from)
                .toList();

        List<CoffeeBasicRecipeListItemResponse> noneCoffeeRecipes = noneCoffeeRecipeRepository
                .findAllByUserUserIdOrderByRecipeIdDesc(BASIC_RECIPE_USER_ID)
                .stream()
                .map(CoffeeBasicRecipeListItemResponse::from)
                .toList();

        List<CoffeeBasicRecipeListItemResponse> recipes = java.util.stream.Stream
                .concat(coffeeRecipes.stream(), noneCoffeeRecipes.stream())
                .sorted((left, right) -> Long.compare(right.getRecipeId(), left.getRecipeId()))
                .toList();

        return CoffeeRecipeListResponse.builder()
                .totalCount(recipes.size())
                .recipes(recipes)
                .build();
    }

    public CoffeePopularRecipeListResponse getPopularRecipeList() {
        List<CoffeePopularRecipeListItemResponse> coffeeRecipes = coffeeRecipeRepository
                .findWithDetailsByUserUserIdNotAndIsSharedTrueOrderBySaveCountDescRecipeIdDesc(POPULAR_EXCLUDED_USER_ID)
                .stream()
                .map(CoffeePopularRecipeListItemResponse::from)
                .toList();

        List<CoffeePopularRecipeListItemResponse> noneCoffeeRecipes = noneCoffeeRecipeRepository
                .findAllByIsSharedTrueOrderBySaveCountDescRecipeIdDesc()
                .stream()
                .filter(entity -> !POPULAR_EXCLUDED_USER_ID.equals(entity.getUser().getUserId()))
                .map(CoffeePopularRecipeListItemResponse::from)
                .toList();

        List<CoffeePopularRecipeListItemResponse> recipes = java.util.stream.Stream
                .concat(coffeeRecipes.stream(), noneCoffeeRecipes.stream())
                .sorted((left, right) -> {
                    int saveCountCompare = Integer.compare(right.getSaveCount(), left.getSaveCount());
                    if (saveCountCompare != 0) {
                        return saveCountCompare;
                    }
                    return Long.compare(right.getRecipeId(), left.getRecipeId());
                })
                .toList();

        return CoffeePopularRecipeListResponse.builder()
                .totalCount(recipes.size())
                .recipes(recipes)
                .build();
    }

    private void validateCoffeeCustomizeRequest(CoffeeRecipeCustomizeCoffeeRequest request) {
        if (request.getRecipeName() == null
                || request.getRecipeCategory() == null
                || request.getCapsule1Id() == null
                || request.getCapsuleTemp() == null
                || request.getCapsule1Size() == null
                || request.getCapsule1Step1() == null
                || request.getCapsule1Step3() == null
                || request.getRecipeLevel() == null) {
            throw new CustomException(ErrorCode.MISSING_REQUIRED_FIELD);
        }

        if (!request.getCapsule1Size().equals(request.getCapsule1Step1() + request.getCapsule1Step3())) {
            throw new CustomException(
                    ErrorCode.INVALID_PARAMETER,
                    "capsule1Size must equal capsule1Step1 + capsule1Step3"
            );
        }

        boolean hasCapsule2Data = request.getCapsule2Id() != null
                || request.getCapsule2Size() != null
                || request.getCapsule2Step2() != null
                || request.getCapsule2Step4() != null;

        if (hasCapsule2Data) {
            if (request.getCapsule2Id() == null
                    || request.getCapsule2Size() == null
                    || request.getCapsule2Step2() == null
                    || request.getCapsule2Step4() == null) {
                throw new CustomException(ErrorCode.MISSING_REQUIRED_FIELD);
            }

            if (!request.getCapsule2Size().equals(request.getCapsule2Step2() + request.getCapsule2Step4())) {
                throw new CustomException(
                        ErrorCode.INVALID_PARAMETER,
                        "capsule2Size must equal capsule2Step2 + capsule2Step4"
                );
            }
        }
    }

    private void validateNoneCoffeeCustomizeRequest(CoffeeRecipeCustomizeNoneCoffeeRequest request) {
        if (request.getRecipeName() == null
                || request.getRecipeCategory() == null
                || request.getRecipeContent() == null
                || request.getRecipeLevel() == null) {
            throw new CustomException(ErrorCode.MISSING_REQUIRED_FIELD);
        }
    }

    private void validateRecipeExists(Long recipeId, Boolean isCoffee) {
        boolean exists = Boolean.TRUE.equals(isCoffee)
                ? coffeeRecipeRepository.existsById(recipeId)
                : noneCoffeeRecipeRepository.existsById(recipeId);

        if (!exists) {
            throw new CustomException(ErrorCode.DATA_NOT_EXIST);
        }
    }

    private UserRecipeListEntity saveUserRecipeList(UsersInfoEntity user, Long recipeId, Boolean isCoffee) {
        String targetRecipeId = String.valueOf(recipeId);

        if (userRecipeListRepository.existsByUserUserIdAndRecipeIdAndIsCoffee(
                user.getUserId(),
                targetRecipeId,
                isCoffee
        )) {
            throw new CustomException(ErrorCode.DATA_ALREADY_EXIST);
        }

        return userRecipeListRepository.save(
                UserRecipeListEntity.builder()
                        .user(user)
                        .recipeId(targetRecipeId)
                        .isCoffee(isCoffee)
                        .build()
        );
    }

    private boolean isCoffeeCategory(RecipeCategory recipeCategory) {
        return recipeCategory == RecipeCategory.COFFEE;
    }
}
