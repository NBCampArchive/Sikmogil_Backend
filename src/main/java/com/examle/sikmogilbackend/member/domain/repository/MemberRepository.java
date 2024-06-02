package com.examle.sikmogilbackend.member.domain.repository;

import com.examle.sikmogilbackend.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByNickname(String nickname);
}
