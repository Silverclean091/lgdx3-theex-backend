package com.lg.theex.domain.mood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.theex.domain.mood.dto.request.MoodCustomProductRequestDTO;
import com.lg.theex.domain.mood.dto.response.CurrentMoodLightResponseDTO;
import com.lg.theex.domain.mood.dto.response.CurrentMoodResponseDTO;
import com.lg.theex.domain.mood.dto.response.CurrentMoodSpeakerResponseDTO;
import com.lg.theex.domain.mood.entity.CurrentMoodStateEntity;
import com.lg.theex.domain.mood.entity.LightCustomEntity;
import com.lg.theex.domain.mood.entity.MoodCustomEntity;
import com.lg.theex.domain.mood.entity.SpeakerCustomEntity;
import com.lg.theex.domain.mood.entity.enumtype.LightColor;
import com.lg.theex.domain.mood.repository.CurrentMoodStateRepository;
import com.lg.theex.domain.mood.repository.LightCustomRepository;
import com.lg.theex.domain.mood.repository.MoodCustomRepository;
import com.lg.theex.domain.mood.repository.SpeakerCustomRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentMoodService {
    private final CurrentMoodStateRepository currentMoodStateRepository;
    private final MoodCustomRepository moodCustomRepository;
    private final LightCustomRepository lightCustomRepository;
    private final SpeakerCustomRepository speakerCustomRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void selectCurrentMood(Long moodId) {
        MoodCustomEntity moodCustom = moodCustomRepository.findById(moodId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "Mood does not exist."));

        MoodCustomProductRequestDTO customProduct = parseCustomProduct(moodCustom.getCustomProduct());
        if (customProduct.lightCustom() == null) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "Light setting is missing in selected mood.");
        }

        LightCustomEntity lightCustom = lightCustomRepository.findById(customProduct.lightCustom())
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "Light custom does not exist."));

        int[] rgb = mapLightColorToRgb(lightCustom.getLightColor());

        CurrentMoodStateEntity state = CurrentMoodStateEntity.builder()
                .stateId(nextStateId())
                .mood(moodCustom)
                .lightR(rgb[0])
                .lightG(rgb[1])
                .lightB(rgb[2])
                .build();

        currentMoodStateRepository.save(state);
    }

    @Transactional(readOnly = true)
    public CurrentMoodResponseDTO getCurrentMood() {
        CurrentMoodStateEntity state = currentMoodStateRepository.findTopByOrderByStateIdDesc()
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "Current mood is not selected."));

        MoodCustomProductRequestDTO customProduct = parseCustomProduct(state.getMood().getCustomProduct());
        if (customProduct.lightCustom() == null || customProduct.speakerCustom() == null) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "Light or speaker setting is missing in current mood.");
        }

        LightCustomEntity lightCustom = lightCustomRepository.findById(customProduct.lightCustom())
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "Light custom does not exist."));

        SpeakerCustomEntity speakerCustom = speakerCustomRepository.findById(customProduct.speakerCustom())
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "Speaker custom does not exist."));

        return new CurrentMoodResponseDTO(
                new CurrentMoodLightResponseDTO(
                        lightCustom.getLightBright(),
                        new int[]{state.getLightR(), state.getLightG(), state.getLightB()}
                ),
                new CurrentMoodSpeakerResponseDTO(
                        speakerCustom.getVolume(),
                        resolveMusicTrackNumber(speakerCustom)
                )
        );
    }

    private MoodCustomProductRequestDTO parseCustomProduct(String customProductJson) {
        try {
            return objectMapper.readValue(customProductJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "Invalid custom product format.");
        }
    }

    private int[] mapLightColorToRgb(LightColor lightColor) {
        if (lightColor == null) {
            throw new BadRequestException(ErrorCode.INVALID_FORMAT, "lightColor is missing.");
        }

        return switch (lightColor) {
            case AMBER -> new int[]{235, 67, 67};
            case BLUE -> new int[]{0, 43, 255};
            case DAYLIGHT -> new int[]{255, 255, 103};
            case ORANGE -> new int[]{255, 128, 0};
            case PURPLE -> new int[]{187, 70, 255};
            case SOFT_WHITE -> new int[]{139, 255, 255};
            case WARM_WHITE -> new int[]{255, 252, 233};
        };
    }

    private Integer resolveMusicTrackNumber(SpeakerCustomEntity speakerCustom) {
        String musicLink = speakerCustom.getMusicLink();
        if (musicLink != null && musicLink.matches("\\d+")) {
            return Integer.parseInt(musicLink);
        }

        throw new BadRequestException(ErrorCode.INVALID_FORMAT, "musicLink must be numeric.");
    }

    private synchronized Long nextStateId() {
        return currentMoodStateRepository.findTopByOrderByStateIdDesc()
                .map(state -> state.getStateId() + 1)
                .orElse(1L);
    }
}
