package com.examle.sikmogilbackend.record.Calendar.api.dto;

import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record CalendarDTO(
        String diaryDate,
        Long diaryWeight,
        String diaryText,
        List<DietPictureDTO> dietPictures,
        List<WorkoutListDTO> workoutLists
) {
}
