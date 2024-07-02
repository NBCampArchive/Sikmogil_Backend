package com.examle.sikmogilbackend.challenge.application;

import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeSaveReqDto;
import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeInfoResDto;
import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.challenge.domain.ChallengeMember;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeMemberRepository;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeRepository;
import com.examle.sikmogilbackend.challenge.exception.AlreadyParticipatingException;
import com.examle.sikmogilbackend.challenge.exception.ChallengeLeaderException;
import com.examle.sikmogilbackend.challenge.exception.ExistsChallengeMemberException;
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
    private final ChallengeMemberRepository challengeMemberRepository;

    // 챌린지 그룹 생성
    @Transactional
    public Long challengeSave(String email, ChallengeSaveReqDto challengeSaveReqDto) {
        Member member = globalUtil.getMemberByEmail(email);

        Challenge challenge = challengeRepository.save(challengeSaveReqDto.toEntity(member));

        return challenge.getChallengeId();
    }

    // 챌린지 그룹 수정

    // 그룹장 챌린지 그룹 삭제

    // 챌린지 그룹 리스트

    // 챌린지 그룹 상세보기
    public ChallengeInfoResDto challengeDetail(String email, Long challengeId) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

        boolean isJoin = challengeMemberRepository.existsByChallengeAndMember(challenge, member);

        return ChallengeInfoResDto.of(member, challenge, isJoin);
    }

    // 챌린지 그룹 참여하기
    @Transactional
    public void joinChallenge(String email, Long challengeId) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

        // 챌린지 그룹장이 본인인지 확인
        validateNotChallengeLeader(member, challenge);

        // 이미 챌린지 그룹에 참여하고 있는지 확인
        validateNotAlreadyParticipating(member, challenge);

        challengeMemberRepository.save(ChallengeMember.builder()
                .challenge(challenge)
                .member(member)
                .build());
    }

    // 참여자 챌린지 그룹 탈퇴하기
    @Transactional
    public void unJoinChallenge(String email, Long challengeId) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

        if (!challengeMemberRepository.existsByChallengeAndMember(challenge, member)) {
            throw new ExistsChallengeMemberException("챌린지 그룹에 참여하고 있지 않습니다.");
        }

        challengeMemberRepository.deleteByChallengeAndMember(challenge, member);
    }

    private void validateNotChallengeLeader(Member member, Challenge challenge) {
        if (member.getMemberId().equals(challenge.getLeader().getMemberId())) {
            throw new ChallengeLeaderException("챌린지 그룹장은 본인의 챌린지 그룹에 참여할 수 없습니다.");
        }
    }

    private void validateNotAlreadyParticipating(Member member, Challenge challenge) {
        boolean isAlreadyParticipating = challenge.getChallengeMembers().stream()
                .anyMatch(challengeMember ->
                        challengeMember.getMember().getMemberId().equals(member.getMemberId()));
        if (isAlreadyParticipating) {
            throw new AlreadyParticipatingException("이미 챌린지 그룹에 참여하고 있습니다.");
        }
    }

}
