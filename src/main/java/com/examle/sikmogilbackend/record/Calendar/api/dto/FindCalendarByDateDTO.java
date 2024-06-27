package com.examle.sikmogilbackend.record.Calendar.api.dto;

import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogInPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import lombok.*;

import java.util.List;

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
    private List<DietLogInPictureDTO> dietLogInPictureDTOS;
    private List<WorkoutListDTO> workoutLists;
}
