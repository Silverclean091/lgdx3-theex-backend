package com.lg.theex.domain.auth.repository;

import com.lg.theex.domain.auth.entity.UserRecipeListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecipeListRepository extends JpaRepository<UserRecipeListEntity, Long> {

    @Override
    <S extends UserRecipeListEntity> S save(S entity);

    boolean existsByUserUserIdAndRecipeIdAndIsCoffee(Long userId, String recipeId, Boolean isCoffee);
}
