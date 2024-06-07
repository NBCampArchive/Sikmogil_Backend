package com.examle.sikmogilbackend.record.WorkoutLog.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record WorkoutLogDTO(
        String workoutDate,
        Long steps,
        Long totalCaloriesBurned
) {
}
