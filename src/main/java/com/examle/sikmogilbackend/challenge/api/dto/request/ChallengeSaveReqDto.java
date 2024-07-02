package com.examle.sikmogilbackend.challenge.api.dto.request;

import com.examle.sikmogilbackend.challenge.domain.Challenge;

public record ChallengeSaveReqDto(
        String topic,
        String name,
        String introduction,
        String gender,
        String activityLevel
) {
    public Challenge toEntity() {
        return Challenge.builder()
                .topic(topic)
                .name(name)
                .introduction(introduction)
                .gender(gender)
                .activityLevel(activityLevel)
                .build();
    }
}
