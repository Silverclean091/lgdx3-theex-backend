package com.lg.theex.domain.mood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.theex.domain.auth.entity.UserMoodListEntity;
import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import com.lg.theex.domain.auth.repository.UserMoodListRepository;
import com.lg.theex.domain.mood.dto.request.MoodCustomRequestDTO;
import com.lg.theex.domain.mood.dto.request.MoodCustomProductRequestDTO;
import com.lg.theex.domain.mood.dto.response.CoffeeCustomDetailResponseDTO;
import com.lg.theex.domain.mood.dto.response.LightCustomDetailResponseDTO;
import com.lg.theex.domain.mood.dto.response.MoodCustomListResponseDTO;
import com.lg.theex.domain.mood.dto.response.MoodCustomProductResponseDTO;
import com.lg.theex.domain.mood.dto.response.SpeakerCustomDetailResponseDTO;
import com.lg.theex.domain.mood.entity.CoffeeCustomEntity;
import com.lg.theex.domain.mood.entity.LightCustomEntity;
import com.lg.theex.domain.mood.entity.MoodColorsetEntity;
import com.lg.theex.domain.mood.entity.MoodCustomEntity;
import com.lg.theex.domain.mood.entity.SpeakerCustomEntity;
import com.lg.theex.domain.mood.repository.CoffeeCustomRepository;
import com.lg.theex.domain.mood.repository.LightCustomRepository;
import com.lg.theex.domain.mood.repository.MoodColorsetRepository;
import com.lg.theex.domain.mood.repository.MoodCustomRepository;
import com.lg.theex.domain.mood.repository.SpeakerCustomRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoodCustomService {
    private static final Long DEMO_USER_ID = 3L;

    private final MoodCustomRepository moodCustomRepository;
    private final MoodColorsetRepository moodColorsetRepository;
    private final LightCustomRepository lightCustomRepository;
    private final SpeakerCustomRepository speakerCustomRepository;
    private final CoffeeCustomRepository coffeeCustomRepository;
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
    public Long updateMoodCustom(Long moodId, MoodCustomRequestDTO requestDTO) {
        if (!moodColorsetRepository.existsById(requestDTO.colorsetId())) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 색상셋입니다.");
        }

        if (!userMoodListRepository.existsByUserUserIdAndMoodMoodId(DEMO_USER_ID, moodId)) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "저장한 무드만 수정할 수 있습니다.");
        }

        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다."));

        UsersInfoEntity user = entityManager.getReference(UsersInfoEntity.class, DEMO_USER_ID);
        MoodColorsetEntity colorset = entityManager.getReference(MoodColorsetEntity.class, requestDTO.colorsetId());

        String customProductJson;
        try {
            customProductJson = objectMapper.writeValueAsString(requestDTO.customProduct());
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "커스텀 제품 정보 형식이 올바르지 않습니다.");
        }

        if (DEMO_USER_ID.equals(moodCustom.getUserId().getUserId())) {
            moodCustom.updateMood(
                    colorset,
                    requestDTO.moodName(),
                    requestDTO.moodMemo(),
                    customProductJson
            );
            return moodCustom.getMoodId();
        }

        MoodCustomEntity copiedMoodCustom = moodCustomRepository.save(
                MoodCustomEntity.builder()
                        .userId(user)
                        .colorsetId(moodCustom.getColorsetId())
                        .moodName(moodCustom.getMoodName())
                        .moodMemo(moodCustom.getMoodMemo())
                        .customProduct(moodCustom.getCustomProduct())
                        .isShared(moodCustom.getIsShared())
                        .saveCount(moodCustom.getSaveCount())
                        .build()
        );

        copiedMoodCustom.updateMood(
                colorset,
                requestDTO.moodName(),
                requestDTO.moodMemo(),
                customProductJson
        );

        saveMoodToUserList(copiedMoodCustom.getMoodId());
        return copiedMoodCustom.getMoodId();
    }

    @Transactional
    public Long shareMoodCustom(Long moodId) {
        if (!moodCustomRepository.existsByMoodIdAndUserIdUserId(moodId, DEMO_USER_ID)) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다.");
        }

        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다."));

        moodCustom.toggleShared();
        return moodCustom.getMoodId();
    }

    @Transactional(readOnly = true)
    public List<MoodCustomListResponseDTO> getSharedMoodCustoms() {
        return moodCustomRepository.findAllByIsSharedTrueOrderByUserIdUserIdAscSaveCountDesc().stream()
                .map(this::toMoodCustomListResponseDTO)
                .toList();
    }

    @Transactional
    public Long saveMoodCustom(Long moodId) {
        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다."));

        if (DEMO_USER_ID.equals(moodCustom.getUserId().getUserId())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "내가 만든 무드는 저장할 수 없습니다.");
        }

        if (!Boolean.TRUE.equals(moodCustom.getIsShared())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "공유된 무드만 저장할 수 있습니다.");
        }

        saveMoodToUserList(moodId);
        moodCustom.increaseSaveCount();
        return moodId;
    }

    @Transactional
    public Long unsaveMoodCustom(Long moodId) {
        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다."));

        if (DEMO_USER_ID.equals(moodCustom.getUserId().getUserId())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "내가 만든 무드는 저장 취소할 수 없습니다.");
        }

        if (!userMoodListRepository.existsByUserUserIdAndMoodMoodId(DEMO_USER_ID, moodId)) {
            throw new BadRequestException(ErrorCode.DATA_NOT_EXIST, "저장된 무드가 존재하지 않습니다.");
        }

        userMoodListRepository.deleteByUserUserIdAndMoodMoodId(DEMO_USER_ID, moodId);
        return moodId;
    }

    @Transactional
    public Long deleteOwnMoodCustom(Long moodId) {
        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 무드입니다."));

        if (!DEMO_USER_ID.equals(moodCustom.getUserId().getUserId())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "내가 만든 무드만 삭제할 수 있습니다.");
        }

        userMoodListRepository.deleteAllByMoodMoodId(moodId);
        moodCustomRepository.delete(moodCustom);
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

    public MoodCustomListResponseDTO toMoodCustomListResponseDTO(MoodCustomEntity moodCustom) {
        MoodCustomProductRequestDTO customProduct;
        try {
            customProduct = objectMapper.readValue(
                    moodCustom.getCustomProduct(),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "저장된 커스텀 제품 정보 형식이 올바르지 않습니다.");
        }

        return new MoodCustomListResponseDTO(
                moodCustom.getMoodId(),
                moodCustom.getMoodName(),
                moodCustom.getColorsetId().getColorsetMain(),
                new MoodCustomProductResponseDTO(
                        toLightCustomDetail(customProduct.lightCustom()),
                        toSpeakerCustomDetail(customProduct.speakerCustom()),
                        toCoffeeCustomDetail(customProduct.coffeeCustom())
                )
        );
    }

    private LightCustomDetailResponseDTO toLightCustomDetail(Long lightCustomId) {
        if (lightCustomId == null) {
            return null;
        }

        LightCustomEntity lightCustom = lightCustomRepository.findById(lightCustomId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 조명 커스텀입니다."));

        return new LightCustomDetailResponseDTO(
                lightCustom.getLightColor(),
                lightCustom.getLightBright()
        );
    }

    private SpeakerCustomDetailResponseDTO toSpeakerCustomDetail(Long speakerCustomId) {
        if (speakerCustomId == null) {
            return null;
        }

        SpeakerCustomEntity speakerCustom = speakerCustomRepository.findById(speakerCustomId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 스피커 커스텀입니다."));

        return new SpeakerCustomDetailResponseDTO(
                speakerCustom.getMusicLink(),
                speakerCustom.getVolume(),
                speakerCustom.getMusicType()
        );
    }

    private CoffeeCustomDetailResponseDTO toCoffeeCustomDetail(Long coffeeCustomId) {
        if (coffeeCustomId == null) {
            return null;
        }

        CoffeeCustomEntity coffeeCustom = coffeeCustomRepository.findById(coffeeCustomId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "존재하지 않는 커피 커스텀입니다."));

        return new CoffeeCustomDetailResponseDTO(
                coffeeCustom.getRecipe().getRecipeName(),
                coffeeCustom.getRecipe().getRecipeCategory(),
                coffeeCustom.getRecipe().getCapsule1() != null ? coffeeCustom.getRecipe().getCapsule1().getCapsuleName() : null,
                coffeeCustom.getRecipe().getCapsule2() != null ? coffeeCustom.getRecipe().getCapsule2().getCapsuleName() : null,
                coffeeCustom.getRecipe().getCapsuleTemp(),
                coffeeCustom.getRecipe().getCapsule1Size(),
                coffeeCustom.getRecipe().getCapsule2Size(),
                coffeeCustom.getRecipe().getCapsule1Step1(),
                coffeeCustom.getRecipe().getCapsule2Step2(),
                coffeeCustom.getRecipe().getCapsule1Step3(),
                coffeeCustom.getRecipe().getCapsule2Step4(),
                coffeeCustom.getRecipe().getAddObj(),
                coffeeCustom.getRecipe().getRecipeMemo(),
                coffeeCustom.getRecipe().getIsExtract(),
                coffeeCustom.getRecipe().getRecipeLevel(),
                coffeeCustom.getRecipe().getIsShared(),
                coffeeCustom.getRecipe().getSaveCount()
        );
    }
}
