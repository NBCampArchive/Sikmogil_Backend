package com.examle.sikmogilbackend.record.Calendar.api.dto;

import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record CalendarDTO(
        String diaryDate,
        Double diaryWeight,
        String diaryText,
        List<DietPictureDTO> dietPictures,
        List<WorkoutListDTO> workoutLists
) {
}
