package com.lg.theex.domain.coffee.repository;

import com.lg.theex.domain.coffee.entity.CoffeeCapsuleEntity;
import com.lg.theex.domain.coffee.entity.enumtype.CapsuleBrands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoffeeCapsuleRepository extends JpaRepository<CoffeeCapsuleEntity, Long> {

    List<CoffeeCapsuleEntity> findAllByCapsuleBrand(CapsuleBrands capsuleBrand);

    List<CoffeeCapsuleEntity> findAllByCapsuleBrandOrderByCapsuleNameAsc(CapsuleBrands capsuleBrand);

    Optional<CoffeeCapsuleEntity> findByCapsuleBrandAndCapsuleName(CapsuleBrands capsuleBrand, String capsuleName);

    boolean existsByCapsuleBrandAndCapsuleName(CapsuleBrands capsuleBrand, String capsuleName);
}
