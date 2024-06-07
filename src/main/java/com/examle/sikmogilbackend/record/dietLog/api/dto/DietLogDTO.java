package com.examle.sikmogilbackend.record.dietLog.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record DietLogDTO(
        Long waterIntake,
        Long totalCalorieEaten,
        String dietDate
) {
}
