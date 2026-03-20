package com.lg.theex.domain.product.repository;

import com.lg.theex.domain.product.entity.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long> {
    List<ProductInfoEntity> findAllByUserUserIdOrderByProductInfoIdAsc(Long userId);
}
