package com.examle.sikmogilbackend.challenge.domain.repository;

import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.challenge.domain.ChallengeMember;
import com.examle.sikmogilbackend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {
    boolean existsByChallengeAndMember(Challenge challenge, Member member);

    void deleteByChallengeAndMember(Challenge challenge, Member member);
}
