package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.Builder;

@Builder
public record DietPictureDTO(
        String date,
        Long dietPictureId,
        String foodPicture,
        String dietDate
) {
}
