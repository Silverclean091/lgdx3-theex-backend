package com.lg.theex.domain.auth.dto.response;

public record MyProductListResponseDTO(
        Long productInfoId,
        String productName,
        Boolean isOn
) {
}
