package com.lg.theex.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductGroupRequestDTO(
        @NotBlank(message = "그룹명 입력은 필수입니다.")
        @Size(min = 1, max = 10, message = "그룹명은 1자 이상, 10자 이하여야 합니다.")
        String groupName
) {
}
