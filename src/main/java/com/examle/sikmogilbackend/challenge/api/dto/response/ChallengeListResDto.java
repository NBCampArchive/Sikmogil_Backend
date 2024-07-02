package com.examle.sikmogilbackend.challenge.api.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ChallengeListResDto(
        Page<ChallengeInfoResDto> challengeInfoResDtos
) {
    public static ChallengeListResDto from(Page<ChallengeInfoResDto> challengeInfoResDto) {
        return ChallengeListResDto.builder()
                .challengeInfoResDtos(challengeInfoResDto)
                .build();
    }

}
