package com.lg.theex.domain.auth.controller;

import com.lg.theex.domain.auth.dto.response.MyProductListResponseDTO;
import com.lg.theex.domain.auth.service.AuthProductService;
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
public class AuthProductController {

    private final AuthProductService authProductService;

    @GetMapping("/my-product-list")
    public ResponseEntity<List<MyProductListResponseDTO>> getMyProductList() {
        return ResponseEntity.ok(authProductService.getMyProductList());
    }
}
