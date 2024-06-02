package com.examle.sikmogilbackend.global.jwt.domain.repository;

import com.examle.sikmogilbackend.global.jwt.domain.Token;
import com.examle.sikmogilbackend.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsByMember(Member member);
    Optional<Token> findByMember(Member member);
    boolean existsByRefreshToken(String refreshToken);
    Optional<Token> findByRefreshToken(String refreshToken);
}
