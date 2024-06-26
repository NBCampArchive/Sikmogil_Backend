package com.examle.sikmogilbackend.record.WorkoutLog.api.dto;


import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import lombok.*;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindWorkoutPictureDTO {
    private Integer lastPage;
    private List<WorkoutListDTO> pictures;
}
