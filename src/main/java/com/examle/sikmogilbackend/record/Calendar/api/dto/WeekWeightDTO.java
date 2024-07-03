package com.examle.sikmogilbackend.record.Calendar.api.dto;

import lombok.Builder;

@Builder
public record WeekWeightDTO(
        String date,
        Double weight
) {
}
