package com.lg.theex.domain.product.service;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.product.dto.request.ProductGroupAssignRequestDTO;
import com.lg.theex.domain.product.dto.request.ProductGroupRequestDTO;
import com.lg.theex.domain.product.entity.ProductGroupEntity;
import com.lg.theex.domain.product.entity.ProductInfoEntity;
import com.lg.theex.domain.product.repository.ProductGroupRepository;
import com.lg.theex.domain.product.repository.ProductInfoRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductGroupService {
    private static final Long DEMO_USER_ID = 3L;

    private final ProductGroupRepository productGroupRepository;
    private final ProductInfoRepository productInfoRepository;
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

    @Transactional
    public int assignProductsToGroup(ProductGroupAssignRequestDTO requestDTO) {
        if (!productGroupRepository.existsByGroupIdAndUserUserId(requestDTO.groupId(), DEMO_USER_ID)) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 그룹입니다.");
        }

        ProductGroupEntity group = entityManager.getReference(ProductGroupEntity.class, requestDTO.groupId());
        List<ProductInfoEntity> productInfos = productInfoRepository.findAllById(requestDTO.productInfoIds());

        if (productInfos.size() != requestDTO.productInfoIds().size()) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 제품이 포함되어 있습니다.");
        }

        for (ProductInfoEntity productInfo : productInfos) {
            productInfo.updateGroup(group);
        }

        return productInfos.size();
    }
}
