package com.lg.theex.domain.mood.dto.response;

public record MoodCustomListResponseDTO(
        String moodName,
        MoodCustomProductResponseDTO customProduct
) {
}
