package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.Builder;

@Builder
public record DietLogDTO(
        Long canEatCalorie,
        Long waterIntake,
        Long totalCalorieEaten,
        String dietDate
) {
}
