package com.examle.sikmogilbackend.record.WorkoutLog.domain.repository;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutListRepository extends JpaRepository<WorkoutList, Long> {

    List<WorkoutList> findWorkoutListsByCalendar(Calendar calendar);
    List<WorkoutList> findWorkoutListsByCalendarAndWorkoutLog(Calendar calendar, WorkoutLog workoutLog);

    Optional<WorkoutList> findWorkoutListByWorkoutListId(Long workoutListId);
}
