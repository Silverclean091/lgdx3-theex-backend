package com.lg.theex.domain.product.service;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.product.dto.request.ProductGroupRequestDTO;
import com.lg.theex.domain.product.entity.ProductGroupEntity;
import com.lg.theex.domain.product.repository.ProductGroupRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductGroupService {
    private static final Long DEMO_USER_ID = 3L;

    private final ProductGroupRepository productGroupRepository;
    private final EntityManager entityManager;

    @Transactional
    public Long createProductGroup(ProductGroupRequestDTO requestDTO) {
        if (productGroupRepository.existsByUserUserIdAndGroupName(DEMO_USER_ID, requestDTO.groupName())) {
            throw new BadRequestException(ErrorCode.DATA_ALREADY_EXIST, "이미 동일한 이름의 그룹이 존재합니다.");
        }

        UsersInfoEntity user = entityManager.getReference(UsersInfoEntity.class, DEMO_USER_ID);

        ProductGroupEntity productGroup = ProductGroupEntity.builder()
                .groupName(requestDTO.groupName())
                .user(user)
                .build();

        return productGroupRepository.save(productGroup).getGroupId();
    }
}
