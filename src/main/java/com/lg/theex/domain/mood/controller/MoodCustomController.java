package com.lg.theex.domain.mood.controller;

import com.lg.theex.domain.mood.dto.request.CoffeeCustomRequestDTO;
import com.lg.theex.domain.mood.dto.request.LightCustomRequestDTO;
import com.lg.theex.domain.mood.dto.request.MoodCustomRequestDTO;
import com.lg.theex.domain.mood.dto.request.SpeakerCustomRequestDTO;
import com.lg.theex.domain.mood.dto.response.CurrentMoodResponseDTO;
import com.lg.theex.domain.mood.dto.response.MoodCustomListResponseDTO;
import com.lg.theex.domain.mood.service.CoffeeCustomService;
import com.lg.theex.domain.mood.service.CurrentMoodService;
import com.lg.theex.domain.mood.service.CurrentMoodSerialService;
import com.lg.theex.domain.mood.service.LightCustomService;
import com.lg.theex.domain.mood.service.MoodCustomService;
import com.lg.theex.domain.mood.service.SpeakerCustomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mood")
public class MoodCustomController {

    private final CoffeeCustomService coffeeCustomService;
    private final LightCustomService lightCustomService;
    private final MoodCustomService moodCustomService;
    private final SpeakerCustomService speakerCustomService;
    private final CurrentMoodService currentMoodService;
    private final CurrentMoodSerialService currentMoodSerialService;

    @PostMapping("/light-custom")
    public ResponseEntity<Map<String, Long>> createLightCustom(
            @RequestBody LightCustomRequestDTO requestDTO
    ) {
        Long lightId = lightCustomService.createLightCustom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("lightId", lightId));
    }

    @PostMapping("/coffee-custom")
    public ResponseEntity<Map<String, Long>> createCoffeeCustom(
            @Valid @RequestBody CoffeeCustomRequestDTO requestDTO
    ) {
        Long coffeeCustomId = coffeeCustomService.createCoffeeCustom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("coffeeCustomId", coffeeCustomId));
    }

    @PostMapping("/speaker-custom")
    public ResponseEntity<Map<String, Long>> createSpeakerCustom(
            @RequestBody SpeakerCustomRequestDTO requestDTO
    ) {
        Long speakerId = speakerCustomService.createSpeakerCustom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("speakerId", speakerId));
    }

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

    @PostMapping("/{moodId}/save")
    public ResponseEntity<Map<String, Long>> saveMoodCustom(
            @PathVariable Long moodId
    ) {
        Long savedMoodId = moodCustomService.saveMoodCustom(moodId);
        return ResponseEntity.ok(Map.of("moodId", savedMoodId));
    }

    @GetMapping("/shared")
    public ResponseEntity<List<MoodCustomListResponseDTO>> getSharedMoodCustoms() {
        return ResponseEntity.ok(moodCustomService.getSharedMoodCustoms());
    }

    @PostMapping("/select/{moodId}")
    public ResponseEntity<Void> selectCurrentMood(
            @PathVariable Long moodId
    ) {
        currentMoodService.selectCurrentMood(moodId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<CurrentMoodResponseDTO> getCurrentMood() {
        return ResponseEntity.ok(currentMoodService.getCurrentMood());
    }

    @PostMapping("/current/send-serial")
    public ResponseEntity<Map<String, String>> sendCurrentMoodToSerial() {
        String payload = currentMoodSerialService.sendCurrentMoodToEsp32();
        return ResponseEntity.ok(Map.of(
                "status", "sent",
                "payload", payload
        ));
    }
}
