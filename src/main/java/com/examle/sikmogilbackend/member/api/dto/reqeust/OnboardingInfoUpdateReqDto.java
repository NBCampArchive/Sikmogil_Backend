package com.examle.sikmogilbackend.member.api.dto.reqeust;

public record OnboardingInfoUpdateReqDto(
        String nickname,
        String height,
        String weight,
        String gender,
        String targetWeight,
        String targetDate,

        String createdDate
) {
}
