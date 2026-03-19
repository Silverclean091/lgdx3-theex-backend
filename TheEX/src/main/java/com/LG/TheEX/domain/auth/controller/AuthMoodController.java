package com.lg.theex.domain.auth.controller;

import com.lg.theex.domain.auth.service.AuthMoodService;
import com.lg.theex.domain.mood.dto.response.MoodCustomListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthMoodController {

    private final AuthMoodService authMoodService;

    @GetMapping("/my-mood-list")
    public ResponseEntity<List<MoodCustomListResponseDTO>> getMyMoodList() {
        return ResponseEntity.ok(authMoodService.getMyMoodList());
    }
}
