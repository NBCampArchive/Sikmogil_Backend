package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.Builder;

@Builder
public record DietListDTO(
        String date,
        Long dietListId,
        Long calorie,
        String foodName,
        String mealTime
) {
}
