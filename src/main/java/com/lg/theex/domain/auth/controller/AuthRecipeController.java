package com.lg.theex.domain.auth.controller;

import com.lg.theex.domain.auth.dto.response.MyRecipeListResponseDTO;
import com.lg.theex.domain.auth.service.AuthRecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "auth-controller")
public class AuthRecipeController {

    private final AuthRecipeService authRecipeService;

    @GetMapping("/my-recipe-list")
    public ResponseEntity<List<MyRecipeListResponseDTO>> getMyRecipeList() {
        return ResponseEntity.ok(authRecipeService.getMyRecipeList());
    }
}
