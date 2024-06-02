package com.examle.sikmogilbackend.member.application;

import com.examle.sikmogilbackend.member.api.dto.reqeust.OnboardingInfoUpdateReqDto;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.ExistsNickNameException;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void onboardingInfoUpdate(String email, OnboardingInfoUpdateReqDto onboardingInfoUpdateReqDto) {
        validateDuplicateNickName(onboardingInfoUpdateReqDto.nickname());
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        member.onboardingUpdate(onboardingInfoUpdateReqDto);
    }

    // nickName 중복검사
    private void validateDuplicateNickName(String nickName) {
        if (memberRepository.existsByNickname(nickName)) {
            throw new ExistsNickNameException();
        }
    }

}
