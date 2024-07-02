package com.examle.sikmogilbackend.challenge.domain.repository;

import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.challenge.domain.ChallengeLeader;
import com.examle.sikmogilbackend.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeLeaderRepository extends JpaRepository<ChallengeLeader, Long> {
    Optional<ChallengeLeader> findByChallengeAndMember(Challenge challenge, Member member);

    boolean existsByChallengeAndMember(Challenge challenge, Member member);
}
