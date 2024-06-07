package com.examle.sikmogilbackend.record.Calendar.domain.repository;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByMember(Member member);

    Calendar findByMemberAndAndDiaryDate(Member member, String diaryDate);

    Boolean existsCalendarByMemberAndDiaryDate(Member member, String diaryDate);
}
