package com.lg.theex.domain.mood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.theex.domain.auth.entity.UserMoodListEntity;
import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.mood.dto.request.MoodCustomRequestDTO;
import com.lg.theex.domain.mood.entity.MoodColorsetEntity;
import com.lg.theex.domain.mood.entity.MoodCustomEntity;
import com.lg.theex.domain.mood.repository.MoodColorsetRepository;
import com.lg.theex.domain.mood.repository.MoodCustomRepository;
import com.lg.theex.domain.mood.repository.UserMoodListRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MoodCustomService {
    private static final Long DEMO_USER_ID = 3L;

    private final MoodCustomRepository moodCustomRepository;
    private final MoodColorsetRepository moodColorsetRepository;
    private final UserMoodListRepository userMoodListRepository;
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    @Transactional
    public Long createMoodCustom(MoodCustomRequestDTO requestDTO) {
        if (!moodColorsetRepository.existsById(requestDTO.colorsetId())) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 색상셋입니다.");
        }

        UsersInfoEntity user = entityManager.getReference(UsersInfoEntity.class, DEMO_USER_ID);
        MoodColorsetEntity colorset = entityManager.getReference(MoodColorsetEntity.class, requestDTO.colorsetId());

        String customProductJson;
        try {
            customProductJson = objectMapper.writeValueAsString(requestDTO.customProduct());
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "커스텀 제품 정보 형식이 올바르지 않습니다.");
        }

        MoodCustomEntity moodCustom = MoodCustomEntity.builder()
                .userId(user)
                .colorsetId(colorset)
                .moodName(requestDTO.moodName())
                .moodMemo(requestDTO.moodMemo())
                .customProduct(customProductJson)
                .isShared(false)
                .saveCount(0)
                .build();

        MoodCustomEntity savedMoodCustom = moodCustomRepository.save(moodCustom);
        saveMoodToUserList(savedMoodCustom.getMoodId());
        return savedMoodCustom.getMoodId();
    }

    @Transactional
    public Long shareMoodCustom(Long moodId) {
        if (!moodCustomRepository.existsByMoodIdAndUserIdUserId(moodId, DEMO_USER_ID)) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다.");
        }

        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다."));

        moodCustom.share();
        return moodCustom.getMoodId();
    }

    @Transactional
    public Long saveMoodCustom(Long moodId) {
        if (!moodCustomRepository.existsByMoodIdAndUserIdUserId(moodId, DEMO_USER_ID)) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다.");
        }

        saveMoodToUserList(moodId);
        return moodId;
    }

    private void saveMoodToUserList(Long moodId) {
        if (userMoodListRepository.existsByUserUserIdAndMoodMoodId(DEMO_USER_ID, moodId)) {
            throw new BadRequestException(ErrorCode.DATA_ALREADY_EXIST, "이미 저장된 무드입니다.");
        }

        UsersInfoEntity user = entityManager.getReference(UsersInfoEntity.class, DEMO_USER_ID);
        MoodCustomEntity mood = entityManager.getReference(MoodCustomEntity.class, moodId);

        UserMoodListEntity userMoodList = UserMoodListEntity.builder()
                .user(user)
                .mood(mood)
                .build();

        userMoodListRepository.save(userMoodList);
    }
}
