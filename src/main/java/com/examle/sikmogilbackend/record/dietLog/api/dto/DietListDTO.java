package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.Builder;

@Builder
public record DietListDTO(
        Long dietListId,
        Long calorie,
        String foodName,
        String foodPicture,
        String mealTime
) {
}
