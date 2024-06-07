package com.examle.sikmogilbackend.record.WorkoutLog.domain.repository;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByMember(Member member);

    WorkoutLog findByMemberAndAndWorkoutDate(Member member, String workoutDate);

    Boolean existsCalendarByMemberAndWorkoutDate(Member member, String workoutDate);
}
