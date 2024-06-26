package com.examle.sikmogilbackend.record.WorkoutLog.domain.repository;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkoutListRepository extends JpaRepository<WorkoutList, Long> {

    List<WorkoutList> findWorkoutListsByCalendar(Calendar calendar);
    List<WorkoutList> findWorkoutListsByCalendarAndWorkoutLog(Calendar calendar, WorkoutLog workoutLog);

    Optional<WorkoutList> findWorkoutListByWorkoutListId(Long workoutListId);

    @Query(value="SELECT wl FROM WorkoutList wl where wl.calendar.member.email = :email and wl.workoutPicture is not null order by wl.workoutListId DESC ")
    Page<WorkoutList> findWorkoutListByByEmailAndWorkoutPictureNotNull(@Param("email") String email,Pageable pageable);
}
