package com.examle.sikmogilbackend.challenge.application;

import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeSaveReqDto;
import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeUpdateReqDto;
import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeInfoResDto;
import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeListResDto;
import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.challenge.domain.ChallengeLeader;
import com.examle.sikmogilbackend.challenge.domain.ChallengeMember;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeLeaderRepository;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeMemberRepository;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeRepository;
import com.examle.sikmogilbackend.challenge.exception.AlreadyParticipatingException;
import com.examle.sikmogilbackend.challenge.exception.ChallengeLeaderException;
import com.examle.sikmogilbackend.challenge.exception.ExistsChallengeMemberException;
import com.examle.sikmogilbackend.challenge.exception.NotChallengeOwnerException;
import com.examle.sikmogilbackend.global.util.GlobalUtil;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {
    private final GlobalUtil globalUtil;
    private final ChallengeRepository challengeRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    private final ChallengeLeaderRepository challengeLeaderRepository;

    // 챌린지 그룹 생성
    @Transactional
    public Long challengeSave(String email, ChallengeSaveReqDto challengeSaveReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = challengeRepository.save(challengeSaveReqDto.toEntity());

        challengeLeaderRepository.save(ChallengeLeader.builder()
                .challenge(challenge)
                .member(member)
                .build());

        return challenge.getChallengeId();
    }

    // 챌린지 그룹 수정
    @Transactional
    public ChallengeInfoResDto challengeUpdate(String email, Long challengeId,
                                               ChallengeUpdateReqDto challengeUpdateReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

        checkChallengeOwnership(member, challenge);

        ChallengeLeader challengeLeader = challengeLeaderRepository.findByChallengeAndMember(challenge, member)
                .orElseThrow();

        boolean isJoin = challengeMemberRepository.existsByChallengeAndMember(challenge, member);

        challenge.update(challengeUpdateReqDto);
        return ChallengeInfoResDto.detailOf(member, challenge, challengeLeader, isJoin);
    }

    // 그룹장 챌린지 그룹 삭제
    @Transactional
    public void challengeDelete(String email, Long challengeId) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

        checkChallengeOwnership(member, challenge);

        ChallengeLeader challengeLeader = challengeLeaderRepository.findByChallengeAndMember(challenge, member)
                .orElseThrow();

        challengeLeaderRepository.delete(challengeLeader);
        challengeRepository.delete(challenge);
    }

    // 챌린지 그룹 리스트 (Topic을 기준으로 조회되어야함.)
    public ChallengeListResDto topicByChallengeAll(String email, String topic, Pageable pageable) {
        Member member = globalUtil.getMemberByEmail(email);

        Page<ChallengeInfoResDto> byTopicWithChallenge = challengeRepository
                .findByTopicWithChallenge(member, topic, pageable);

        return ChallengeListResDto.from(byTopicWithChallenge);
    }

    // 챌린지 그룹 상세보기
    public ChallengeInfoResDto challengeDetail(String email, Long challengeId) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

        boolean isJoin = challengeMemberRepository.existsByChallengeAndMember(challenge, member);

        ChallengeLeader challengeLeader = challengeLeaderRepository.findByChallengeAndMember(challenge, member)
                .orElseThrow();

        return ChallengeInfoResDto.detailOf(member, challenge, challengeLeader, isJoin);
    }

    // 챌린지 그룹 참여하기
    @Transactional
    public void joinChallenge(String email, Long challengeId) {
        Member member = globalUtil.getMemberByEmail(email);
        Challenge challenge = globalUtil.getChallengeById(challengeId);

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

    private void checkChallengeOwnership(Member member, Challenge challenge) {
        if (!challengeLeaderRepository.existsByChallengeAndMember(challenge, member)) {
            throw new NotChallengeOwnerException();
        }
    }

    private void validateNotChallengeLeader(Member member, Challenge challenge) {
        if (challengeLeaderRepository.existsByChallengeAndMember(challenge, member)) {
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
