package com.examle.sikmogilbackend.record.Calendar.api.dto;

import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindCalendarByDateDTO {
    private String diaryDate;
    private Long diaryWeight;
    private String diaryText;
    private Long canEatCalorie;
    private Long waterIntake;
    private Long totalCalorieEaten;
    private List<DietPictureDTO> dietPictureDTOS;
    //    private List<DietLogInPictureDTO> dietLogInPictureDTOS;
    private List<WorkoutListDTO> workoutLists;
}
