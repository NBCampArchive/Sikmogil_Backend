package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.Builder;

@Builder
public record DietLogDTO(
        Long waterIntake,
        Long totalCalorieEaten,
        String dietDate
) {
}
