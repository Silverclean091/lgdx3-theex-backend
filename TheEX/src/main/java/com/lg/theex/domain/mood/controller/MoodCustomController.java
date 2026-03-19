package com.lg.theex.domain.mood.controller;

import com.lg.theex.domain.mood.dto.request.MoodCustomRequestDTO;
import com.lg.theex.domain.mood.service.MoodCustomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mood")
public class MoodCustomController {

    private final MoodCustomService moodCustomService;

    @PostMapping("/custom")
    public ResponseEntity<Map<String, Long>> createMoodCustom(
            @Valid @RequestBody MoodCustomRequestDTO requestDTO
    ) {
        Long moodId = moodCustomService.createMoodCustom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("moodId", moodId));
    }

    @PatchMapping("/{moodId}/share")
    public ResponseEntity<Map<String, Long>> shareMoodCustom(
            @PathVariable Long moodId
    ) {
        Long sharedMoodId = moodCustomService.shareMoodCustom(moodId);
        return ResponseEntity.ok(Map.of("moodId", sharedMoodId));
    }
}
