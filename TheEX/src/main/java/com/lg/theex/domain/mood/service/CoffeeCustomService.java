package com.lg.theex.domain.mood.service;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.mood.dto.request.CoffeeCustomRequestDTO;
import com.lg.theex.domain.mood.entity.CoffeeCustomEntity;
import com.lg.theex.domain.mood.repository.CoffeeCustomRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoffeeCustomService {
    private static final String DEFAULT_PRODUCT_CODE = "COFFEE0001";

    private final CoffeeCustomRepository coffeeCustomRepository;
    private final EntityManager entityManager;

    @Transactional
    public Long createCoffeeCustom(CoffeeCustomRequestDTO requestDTO) {
        if (entityManager.find(CoffeeRecipeEntity.class, requestDTO.recipeId()) == null) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 레시피입니다.");
        }

        CoffeeRecipeEntity recipe = entityManager.getReference(CoffeeRecipeEntity.class, requestDTO.recipeId());

        CoffeeCustomEntity coffeeCustom = CoffeeCustomEntity.builder()
                .recipe(recipe)
                .productCode(DEFAULT_PRODUCT_CODE)
                .build();

        return coffeeCustomRepository.save(coffeeCustom).getCoffeeCustomId();
    }
}
