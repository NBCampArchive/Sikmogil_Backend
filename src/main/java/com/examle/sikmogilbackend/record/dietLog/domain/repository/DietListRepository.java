package com.examle.sikmogilbackend.record.dietLog.domain.repository;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.dietLog.domain.DietList;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DietListRepository extends JpaRepository<DietList, Long> {
    List<DietList> findDietListByCalendarAndDietLog(Calendar calendar, DietLog dietLog);

    Optional<DietList> findDietListByDietListId(Long dietListId);
}
