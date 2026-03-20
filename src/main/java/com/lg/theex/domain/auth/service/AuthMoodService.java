package com.lg.theex.domain.auth.service;

import com.lg.theex.domain.auth.repository.UserMoodListRepository;
import com.lg.theex.domain.mood.dto.response.MoodCustomListResponseDTO;
import com.lg.theex.domain.mood.service.MoodCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthMoodService {
    private static final Long DEMO_USER_ID = 3L;

    private final UserMoodListRepository userMoodListRepository;
    private final MoodCustomService moodCustomService;

    @Transactional(readOnly = true)
    public List<MoodCustomListResponseDTO> getMyMoodList() {
        return userMoodListRepository.findAllByUserUserId(DEMO_USER_ID).stream()
                .map(userMoodList -> moodCustomService.toMoodCustomListResponseDTO(userMoodList.getMood()))
                .toList();
    }
}
