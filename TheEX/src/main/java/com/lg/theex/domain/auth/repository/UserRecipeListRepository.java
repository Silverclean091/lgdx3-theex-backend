package com.lg.theex.domain.auth.repository;

import com.lg.theex.domain.auth.entity.UserRecipeListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecipeListRepository extends JpaRepository<UserRecipeListEntity, Long> {

    @Override
    <S extends UserRecipeListEntity> S save(S entity);

    List<UserRecipeListEntity> findAllByUserUserId(Long userId);
    boolean existsByUserUserIdAndRecipeIdAndIsCoffee(Long userId, String recipeId, Boolean isCoffee);
}
