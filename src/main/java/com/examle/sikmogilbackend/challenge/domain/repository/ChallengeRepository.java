package com.examle.sikmogilbackend.challenge.domain.repository;

import com.examle.sikmogilbackend.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeCustomRepository {
}
