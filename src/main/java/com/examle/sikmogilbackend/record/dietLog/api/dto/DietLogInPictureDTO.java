package com.examle.sikmogilbackend.record.dietLog.api.dto;

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
public class DietLogInPictureDTO {
    private List<DietPictureDTO> dietPictureDTOS;
}
