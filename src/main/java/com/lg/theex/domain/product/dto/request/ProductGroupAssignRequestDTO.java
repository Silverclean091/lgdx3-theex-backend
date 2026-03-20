package com.lg.theex.domain.product.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductGroupAssignRequestDTO(
        @NotNull(message = "그룹 ID는 필수입니다.")
        Long groupId,

        @NotEmpty(message = "제품 ID 목록은 비어 있을 수 없습니다.")
        List<Long> productInfoIds
) {
}
