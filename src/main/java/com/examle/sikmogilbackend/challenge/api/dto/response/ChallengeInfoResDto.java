package com.examle.sikmogilbackend.challenge.api.dto.response;

import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.member.domain.Member;
import java.util.List;
import lombok.Builder;

@Builder
public record ChallengeInfoResDto(
        Long myMemberId,
        boolean isJoin,
        Long challengeLeaderId,
        String topic,
        String name,
        String introduction,
        String gender,
        String activityLevel,
        List<ChallengeMemberResDto> challengeMembers
) {
    public static ChallengeInfoResDto of(Member member, Challenge challenge, boolean isJoin) {
        List<ChallengeMemberResDto> challengeMemberResDtos = challenge.getChallengeMembers().stream()
                .map(c -> ChallengeMemberResDto.from(c.getMember()))
                .toList();

        return ChallengeInfoResDto.builder()
                .myMemberId(member.getMemberId())
                .isJoin(isJoin)
                .challengeLeaderId(challenge.getChallengeId())
                .topic(challenge.getTopic())
                .name(challenge.getName())
                .introduction(challenge.getIntroduction())
                .gender(challenge.getGender())
                .activityLevel(challenge.getActivityLevel())
                .challengeMembers(challengeMemberResDtos)
                .build();
    }
}
