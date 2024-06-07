package com.examle.sikmogilbackend.record.dietLog.application;

import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietListDTO;
import com.examle.sikmogilbackend.record.dietLog.domain.DietList;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import com.examle.sikmogilbackend.record.dietLog.domain.repository.DietListRepository;
import com.examle.sikmogilbackend.record.dietLog.exception.DietListNotEqualsException;
import com.examle.sikmogilbackend.record.dietLog.exception.DietListNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DietListService {
    private final DietListRepository dietListRepository;

    private final CalendarService calendarService;
    private final DietLogService dietLogService;

    @Transactional
    public List<DietListDTO> findDietListByDate (String email, String date) {
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);

        List<DietList> dietLists =
                dietListRepository.findDietListByCalendarAndDietLog(calendar, dietLog);

        return dietLists.stream()
                .map(DietList::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addDiet(String email, String date, DietListDTO dietListDTO){
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        DietList dietList = DietList.builder()
                        .foodPicture(dietListDTO.foodPicture())
                        .mealTime(dietListDTO.mealTime())
                        .calorie(dietListDTO.calorie())
                        .foodName(dietListDTO.foodName())
                        .calendar(calendar)
                        .dietLog(dietLog)
                        .build();
        dietListRepository.save(dietList);
    }

    @Transactional
    public void deleteDiet(String email, String date, Long dietListId) {
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        DietList dietList = dietListRepository.findDietListByDietListId(dietListId).orElseThrow(DietListNotFoundException::new);
        checkEqualsDiet(dietLog, dietList);
        dietListRepository.delete(dietList);
    }

    private void checkEqualsDiet(DietLog dietLog, DietList dietList) {
        log.info("삭제 중");
        if (!(dietLog.getDietLogId() == dietList.getDietLog().getDietLogId())) {
            throw new DietListNotEqualsException();
        }
    }
}
