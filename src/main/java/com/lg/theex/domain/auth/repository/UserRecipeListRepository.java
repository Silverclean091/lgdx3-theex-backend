package com.lg.theex.domain.auth.repository;

import com.lg.theex.domain.auth.entity.UserRecipeListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRecipeListRepository extends JpaRepository<UserRecipeListEntity, Long> {
    List<UserRecipeListEntity> findAllByUserUserId(Long userId);
    boolean existsByUserUserIdAndRecipeIdAndIsCoffee(Long userId, String recipeId, Boolean isCoffee);
}
