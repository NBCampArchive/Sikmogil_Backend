package com.examle.sikmogilbackend.record.Calendar.api.dto;

import lombok.Builder;

@Builder
public record CalendarDTO(
        String diaryDate,
        Long diaryWeight,
        String diaryText
) {
}
