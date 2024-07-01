package com.examle.sikmogilbackend.challenge.api.dto.request;

import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.member.domain.Member;

public record ChallengeSaveReqDto(
        String topic,
        String name,
        String introduction,
        String gender,
        String activityLevel
) {
    public Challenge toEntity(Member member) {
        return Challenge.builder()
                .leader(member)
                .topic(topic)
                .name(name)
                .introduction(introduction)
                .gender(gender)
                .activityLevel(activityLevel)
                .build();
    }
}
