package com.examle.sikmogilbackend.challenge.domain.repository;

import com.examle.sikmogilbackend.challenge.domain.ChallengeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {
}
