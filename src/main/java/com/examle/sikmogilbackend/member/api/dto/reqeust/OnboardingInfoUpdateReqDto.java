package com.examle.sikmogilbackend.member.api.dto.reqeust;

import lombok.Builder;

@Builder
public record OnboardingInfoUpdateReqDto(
        String picture,
        String nickname,
        String height,
        String weight,
        String gender,
        String targetWeight,
        String targetDate,
        Long canEatCalorie,
        String createdDate,
        String remindTime

) {
}
