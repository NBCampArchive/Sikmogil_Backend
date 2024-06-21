package com.examle.sikmogilbackend.record.dietLog.application;

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

    private final DietLogService dietLogService;

    @Transactional
    public List<DietListDTO> findDietListByDate (String email, String date) {
        log.info("findDietListByDate ");
        log.info("email = "+email);
        log.info("date = "+date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);

        List<DietList> dietLists =
                dietListRepository.findDietListByDietLog(dietLog);

        return dietLists.stream()
                .map(DietList::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addDietList(String email, String date, DietListDTO dietListDTO){
        log.info("addDietList ");
        log.info("email = "+email);
        log.info("date = "+date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        DietList dietList = DietList.builder()
                        .mealTime(dietListDTO.mealTime())
                        .calorie(dietListDTO.calorie())
                        .foodName(dietListDTO.foodName())
                        .dietLog(dietLog)
                        .build();
        dietListRepository.save(dietList);
    }

    @Transactional
    public void deleteDietList(String email, String date, Long dietListId) {
        log.info("deleteDietList ");
        log.info("email = "+email);
        log.info("date = "+date);

        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        DietList dietList = dietListRepository.findDietListByDietListId(dietListId).orElseThrow(DietListNotFoundException::new);
        checkEqualsDiet(dietLog, dietList);
        dietListRepository.delete(dietList);
    }

    @Transactional
    private void checkEqualsDiet(DietLog dietLog, DietList dietList) {
        log.info("삭제 중");
        if (!(dietLog.getDietLogId() == dietList.getDietLog().getDietLogId())) {
            throw new DietListNotEqualsException();
        }
    }
}
