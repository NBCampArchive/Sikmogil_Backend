package com.examle.sikmogilbackend.record.dietLog.domain.repository;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DietLogRepository extends JpaRepository<DietLog, Long> {
    List<DietLog> findByMember(Member member);

    DietLog findByMemberAndAndDietDate(Member member, String dietDate);

    Boolean existsDietLogByMemberAndDietDate(Member member, String dietDate);

}
