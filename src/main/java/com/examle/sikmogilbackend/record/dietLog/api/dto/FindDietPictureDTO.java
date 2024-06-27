package com.examle.sikmogilbackend.record.dietLog.api.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindDietPictureDTO {
    private Integer lastPage;
    private List<DietPictureDTO> pictures;
}
