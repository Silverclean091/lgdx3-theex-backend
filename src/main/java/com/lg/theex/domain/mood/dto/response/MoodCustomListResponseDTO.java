package com.lg.theex.domain.mood.dto.response;

public record MoodCustomListResponseDTO(
        Long moodId,
        String moodName,
        String colorsetMain,
        MoodCustomProductResponseDTO customProduct
) {
}
