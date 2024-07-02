package com.examle.sikmogilbackend.challenge.domain.repository;

import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeInfoResDto;
import com.examle.sikmogilbackend.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeCustomRepository {
    Page<ChallengeInfoResDto> findByTopicWithChallenge(Member member, String topic, Pageable pageable);
}
