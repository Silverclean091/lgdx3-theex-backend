package com.lg.theex.domain.coffee.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CoffeeRecipeListResponse {

    private Long userId;
    private int totalCount;
    private List<CoffeeRecipeListItemResponse> recipes;
}
