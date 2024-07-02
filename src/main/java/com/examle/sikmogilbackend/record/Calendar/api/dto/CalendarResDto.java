package com.examle.sikmogilbackend.record.Calendar.api.dto;

import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import lombok.Builder;

@Builder
public record CalendarResDto(
        String diaryDate,
        Long diaryWeight,
        String diaryText,
        DietPictureDTO dietPicture,
        WorkoutListDTO workoutList
) {
}
