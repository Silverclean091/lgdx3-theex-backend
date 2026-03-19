package com.lg.theex.domain.coffee.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CoffeePopularRecipeListResponse {

    private Long excludedUserId;
    private int totalCount;
    private List<CoffeeRecipeListItemResponse> recipes;
}
