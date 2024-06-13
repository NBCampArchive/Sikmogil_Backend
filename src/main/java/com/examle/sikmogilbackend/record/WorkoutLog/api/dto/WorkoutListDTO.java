package com.examle.sikmogilbackend.record.WorkoutLog.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record WorkoutListDTO(
        String date,
        Long workoutListId,
        String performedWorkout,
        Long workoutTime,
        Long workoutIntensity,
        String workoutPicture,
        Long calorieBurned
) {
}
