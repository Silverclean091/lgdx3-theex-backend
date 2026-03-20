package com.lg.theex.domain.product.repository;

import com.lg.theex.domain.product.entity.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long> {
}
