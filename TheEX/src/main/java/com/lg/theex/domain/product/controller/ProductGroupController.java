package com.lg.theex.domain.product.controller;

import com.lg.theex.domain.mood.dto.request.LightCustomRequestDTO;
import com.lg.theex.domain.product.dto.request.ProductGroupRequestDTO;
import com.lg.theex.domain.product.dto.request.ProductGroupAssignRequestDTO;
import com.lg.theex.domain.mood.dto.request.SpeakerCustomRequestDTO;
import com.lg.theex.domain.mood.service.LightCustomService;
import com.lg.theex.domain.product.service.ProductGroupService;
import com.lg.theex.domain.mood.service.SpeakerCustomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductGroupController {

    private final LightCustomService lightCustomService;
    private final ProductGroupService productGroupService;
    private final SpeakerCustomService speakerCustomService;

    @PostMapping("/create-group")
    public ResponseEntity<Map<String, Long>> createGroup(
            @Valid @RequestBody ProductGroupRequestDTO requestDTO
    ) {
        Long groupId = productGroupService.createProductGroup(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("groupId", groupId));
    }

    @PostMapping("/light-custom")
    public ResponseEntity<Map<String, Long>> createLightCustom(
            @RequestBody LightCustomRequestDTO requestDTO
    ) {
        Long lightId = lightCustomService.createLightCustom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("lightId", lightId));
    }

    @PostMapping("/speaker-custom")
    public ResponseEntity<Map<String, Long>> createSpeakerCustom(
            @RequestBody SpeakerCustomRequestDTO requestDTO
    ) {
        Long speakerId = speakerCustomService.createSpeakerCustom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("speakerId", speakerId));
    }

    @PatchMapping("/group")
    public ResponseEntity<Map<String, Long>> assignProductGroup(
            @Valid @RequestBody ProductGroupAssignRequestDTO requestDTO
    ) {
        int updatedCount = productGroupService.assignProductsToGroup(requestDTO);
        return ResponseEntity.ok(Map.of("updatedCount", (long) updatedCount));
    }
}
