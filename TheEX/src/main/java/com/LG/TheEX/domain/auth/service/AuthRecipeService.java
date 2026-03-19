package com.lg.theex.domain.auth.service;

import com.lg.theex.domain.auth.dto.response.MyRecipeListResponseDTO;
import com.lg.theex.domain.auth.entity.UserRecipeListEntity;
import com.lg.theex.domain.auth.repository.UserRecipeListRepository;
import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.coffee.entity.NoneCoffeeRecipeEntity;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthRecipeService {
    private static final Long DEMO_USER_ID = 3L;

    private final UserRecipeListRepository userRecipeListRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<MyRecipeListResponseDTO> getMyRecipeList() {
        return userRecipeListRepository.findAllByUserUserId(DEMO_USER_ID).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private MyRecipeListResponseDTO toResponseDTO(UserRecipeListEntity userRecipeList) {
        Long recipeId;
        try {
            recipeId = Long.parseLong(userRecipeList.getRecipeId());
        } catch (NumberFormatException e) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "레시피 ID 형식이 올바르지 않습니다.");
        }

        if (Boolean.TRUE.equals(userRecipeList.getIsCoffee())) {
            CoffeeRecipeEntity coffeeRecipe = entityManager.find(CoffeeRecipeEntity.class, recipeId);
            if (coffeeRecipe == null) {
                throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 커피 레시피입니다.");
            }
            return new MyRecipeListResponseDTO(
                    recipeId,
                    true,
                    coffeeRecipe.getRecipeName()
            );
        }

        NoneCoffeeRecipeEntity noneCoffeeRecipe = entityManager.find(NoneCoffeeRecipeEntity.class, recipeId);
        if (noneCoffeeRecipe == null) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 논커피 레시피입니다.");
        }
        return new MyRecipeListResponseDTO(
                recipeId,
                false,
                noneCoffeeRecipe.getRecipeName()
        );
    }
}
