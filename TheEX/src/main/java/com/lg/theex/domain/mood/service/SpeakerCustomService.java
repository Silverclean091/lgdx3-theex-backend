package com.lg.theex.domain.mood.service;

import com.lg.theex.domain.mood.dto.request.SpeakerCustomRequestDTO;
import com.lg.theex.domain.mood.entity.SpeakerCustomEntity;
import com.lg.theex.domain.mood.repository.SpeakerCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpeakerCustomService {
    private static final String DEFAULT_PRODUCT_CODE = "CODE0001";

    private final SpeakerCustomRepository speakerCustomRepository;

    @Transactional
    public Long createSpeakerCustom(SpeakerCustomRequestDTO requestDTO) {
        SpeakerCustomEntity speakerCustom = SpeakerCustomEntity.builder()
                .productCode(DEFAULT_PRODUCT_CODE)
                .musicLink(requestDTO.musicLink())
                .volume(requestDTO.volume())
                .build();

        return speakerCustomRepository.save(speakerCustom).getSpeakerId();
    }
}
