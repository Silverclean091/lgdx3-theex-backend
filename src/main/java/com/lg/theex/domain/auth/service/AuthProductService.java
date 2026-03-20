package com.lg.theex.domain.auth.service;

import com.lg.theex.domain.auth.dto.response.MyProductListResponseDTO;
import com.lg.theex.domain.product.repository.ProductInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthProductService {
    private static final Long DEMO_USER_ID = 3L;

    private final ProductInfoRepository productInfoRepository;

    @Transactional(readOnly = true)
    public List<MyProductListResponseDTO> getMyProductList() {
        return productInfoRepository.findAllByUserUserIdOrderByProductInfoIdAsc(DEMO_USER_ID).stream()
                .map(productInfo -> new MyProductListResponseDTO(
                        productInfo.getProductInfoId(),
                        productInfo.getProductName(),
                        productInfo.getIsOn()
                ))
                .toList();
    }
}
