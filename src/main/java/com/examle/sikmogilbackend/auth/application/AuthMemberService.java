package com.examle.sikmogilbackend.auth.application;

import com.examle.sikmogilbackend.auth.api.dto.response.MemberLoginResDto;
import com.examle.sikmogilbackend.auth.api.dto.response.UserInfo;
import com.examle.sikmogilbackend.auth.exception.ExistsMemberEmailException;
import com.examle.sikmogilbackend.auth.exception.NotFoundGithubEmailException;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.Role;
import com.examle.sikmogilbackend.member.domain.SocialType;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthMemberService {
    private final MemberRepository memberRepository;

    public AuthMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberLoginResDto saveUserInfo(UserInfo userInfo, SocialType provider) {
        validateNotFoundEmail(userInfo.email());

        Member member = getExistingMemberOrCreateNew(userInfo, provider);

        validateSocialType(member, provider);

        return MemberLoginResDto.from(member);
    }

    private void validateNotFoundEmail(String email) {
        if (email == null) {
            throw new NotFoundGithubEmailException();
        }
    }

    private Member getExistingMemberOrCreateNew(UserInfo userInfo, SocialType provider) {
        return memberRepository.findByEmail(userInfo.email()).orElseGet(() -> createMember(userInfo, provider));
    }

    private Member createMember(UserInfo userInfo, SocialType provider) {
        return memberRepository.save(
                Member.builder()
                        .email(userInfo.email())
                        .name(userInfo.name())
                        .picture(userInfo.picture())
                        .socialType(provider)
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    private void validateSocialType(Member member, SocialType provider) {
        if (!provider.equals(member.getSocialType())) {
            throw new ExistsMemberEmailException();
        }
    }

}
