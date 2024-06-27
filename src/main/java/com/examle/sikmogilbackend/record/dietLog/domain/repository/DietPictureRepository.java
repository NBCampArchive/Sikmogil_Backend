package com.examle.sikmogilbackend.record.dietLog.domain.repository;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import com.examle.sikmogilbackend.record.dietLog.domain.DietPicture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DietPictureRepository extends JpaRepository<DietPicture, Long> {

    List<DietPicture> findByCalendar(Calendar calendar);
    List<DietPicture> findDietPictureByCalendarAndDietLog(Calendar calendar, DietLog dietLog);

    Optional<DietPicture> findDietPictureByDietPictureId(Long dietPictureId);

    @Query(value = "select dp from DietPicture dp where dp.calendar.member.email = :email order by dp.dietPictureId DESC ")
    Page<DietPicture> findDietPicturesByEmail(@Param("email") String email, Pageable pageable);
}
