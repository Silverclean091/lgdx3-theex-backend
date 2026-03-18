package com.lg.theex.domain.mood.service;

import com.lg.theex.domain.mood.dto.request.LightCustomRequestDTO;
import com.lg.theex.domain.mood.entity.LightCustomEntity;
import com.lg.theex.domain.mood.repository.LightCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LightCustomService {
    private static final String DEFAULT_PRODUCT_CODE = "CODE0001";

    private final LightCustomRepository lightCustomRepository;

    @Transactional
    public Long createLightCustom(LightCustomRequestDTO requestDTO) {
        LightCustomEntity lightCustom = LightCustomEntity.builder()
                .productCode(DEFAULT_PRODUCT_CODE)
                .lightColor(requestDTO.lightColor())
                .lightBright(requestDTO.lightBright())
                .build();

        return lightCustomRepository.save(lightCustom).getLightId();
    }
}
