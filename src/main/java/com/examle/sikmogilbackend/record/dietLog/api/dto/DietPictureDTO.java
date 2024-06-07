package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.Builder;

@Builder
public record DietPictureDTO(
        Long dietPictureId,
        String foodPicture,
        String dietDate
) {
}
