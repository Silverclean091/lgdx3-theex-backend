package com.lg.theex.domain.product.repository;

import com.lg.theex.domain.product.entity.ProductGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroupEntity, Long> {
    boolean existsByUserUserIdAndGroupName(Long userId, String groupName);
    boolean existsByGroupIdAndUserUserId(Long groupId, Long userId);
}
