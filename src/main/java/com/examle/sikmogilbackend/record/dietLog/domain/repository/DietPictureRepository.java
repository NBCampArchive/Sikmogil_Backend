package com.examle.sikmogilbackend.record.dietLog.domain.repository;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import com.examle.sikmogilbackend.record.dietLog.domain.DietPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DietPictureRepository extends JpaRepository<DietPicture, Long> {

    List<DietPicture> findByCalendar(Calendar calendar);
    List<DietPicture> findDietPictureByCalendarAndDietLog(Calendar calendar, DietLog dietLog);

    Optional<DietPicture> findDietPictureByDietPictureId(Long dietPictureId);
}
