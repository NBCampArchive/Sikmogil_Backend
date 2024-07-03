package com.examle.sikmogilbackend.challenge.api.dto.response;

import com.examle.sikmogilbackend.member.domain.Member;
import lombok.Builder;

@Builder
public record ChallengeMemberResDto(
        Long memberId,
        String nickname,
        String picture
) {
    public static ChallengeMemberResDto from(Member member) {
        return ChallengeMemberResDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .picture(member.getPicture())
                .build();
    }
}
