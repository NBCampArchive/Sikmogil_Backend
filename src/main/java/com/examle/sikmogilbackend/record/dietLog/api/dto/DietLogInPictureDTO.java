package com.examle.sikmogilbackend.record.dietLog.api.dto;

import com.examle.sikmogilbackend.record.dietLog.domain.DietPicture;
import lombok.*;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietLogInPictureDTO {
    private Long waterIntake;
    private Long totalCalorieEaten;
    private List<DietPictureDTO> dietPictureDTOS;
}
