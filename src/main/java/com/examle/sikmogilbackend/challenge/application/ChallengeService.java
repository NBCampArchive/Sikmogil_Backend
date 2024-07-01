package com.examle.sikmogilbackend.challenge.application;

import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeSaveReqDto;
import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeRepository;
import com.examle.sikmogilbackend.global.util.GlobalUtil;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {
    private final GlobalUtil globalUtil;
    private final ChallengeRepository challengeRepository;

    // 챌린지 그룹 생성
    @Transactional
    public Long challengeSave(String email, ChallengeSaveReqDto challengeSaveReqDto) {
        Member member = globalUtil.getMemberByEmail(email);

        Challenge challenge = challengeRepository.save(challengeSaveReqDto.toEntity(member));

        return challenge.getChallengeId();
    }

    // 챌린지 그룹 수정

    // 챌린지 그룹 삭제

    // 챌린지 그룹 리스트

    // 챌린지 그룹 상세보기

    // 챌린지 그룹 참여하기

    // 챌린지 그룹 탈퇴하기


}
