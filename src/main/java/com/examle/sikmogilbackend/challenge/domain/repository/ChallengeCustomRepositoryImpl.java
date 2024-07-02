package com.examle.sikmogilbackend.challenge.domain.repository;

import static com.examle.sikmogilbackend.challenge.domain.QChallenge.challenge;

import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeInfoResDto;
import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeCustomRepositoryImpl implements ChallengeCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ChallengeInfoResDto> findByTopicWithChallenge(Member member, String topic, Pageable pageable) {
        long total = queryFactory
                .selectFrom(challenge)
                .where(challenge.topic.eq(topic))
                .fetchCount();

        List<Challenge> challenges = queryFactory
                .selectFrom(challenge)
                .where(challenge.topic.eq(topic))
                .orderBy(challenge.challengeId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ChallengeInfoResDto> challengeInfoResDtos = challenges.stream()
                .map(c -> ChallengeInfoResDto.of(member, c))
                .toList();

        return new PageImpl<>(challengeInfoResDtos, pageable, total);
    }

}
