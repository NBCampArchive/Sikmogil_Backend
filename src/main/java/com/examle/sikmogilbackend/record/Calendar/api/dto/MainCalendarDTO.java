package com.examle.sikmogilbackend.record.Calendar.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MainCalendarDTO(
        String createDate,
        String targetDate,
        String targetWeight,
        List<WeekWeightDTO> weekWeights
) {
}
