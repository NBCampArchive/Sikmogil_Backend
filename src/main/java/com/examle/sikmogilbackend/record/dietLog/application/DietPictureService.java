package com.examle.sikmogilbackend.record.dietLog.application;

import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.Calendar.domain.repository.CalendarRepository;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.domain.DietList;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import com.examle.sikmogilbackend.record.dietLog.domain.DietPicture;
import com.examle.sikmogilbackend.record.dietLog.domain.repository.DietListRepository;
import com.examle.sikmogilbackend.record.dietLog.domain.repository.DietPictureRepository;
import com.examle.sikmogilbackend.record.dietLog.exception.DietListNotEqualsException;
import com.examle.sikmogilbackend.record.dietLog.exception.DietListNotFoundException;
import com.examle.sikmogilbackend.record.dietLog.exception.DietPictureNotFoundException;
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
public class DietPictureService {

    private final DietPictureRepository dietPictureRepository;
    private final CalendarService calendarService;
    private final DietLogService dietLogService;
    private final MemberService memberService;

    @Transactional
    public List<DietPictureDTO> findDietPictureByDate (String email, String date) {
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);

        List<DietPicture> dietPictures = dietPictureRepository.findDietPictureByCalendarAndDietLog(calendar, dietLog);

        return dietPictures.stream()
                .map(DietPicture::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<DietPictureDTO> findDietPictureByDate (Calendar calendar) {
        List<DietPicture> dietPictures = dietPictureRepository.findByCalendar(calendar);
        return dietPictures.stream()
                .map(DietPicture::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DietPicture> findDietPictures(String email) {
        Member member = memberService.findMember(email);
        return member.getCalendars().stream()
                .flatMap(calendar -> calendar.getDietPictures().stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public void addDietPicture(String email, String date, DietPictureDTO dietPictureDTO){
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        DietPicture dietPicture = DietPicture.builder()
                .foodPicture(dietPictureDTO.foodPicture())
                .dietDate(dietPictureDTO.dietDate())
                .calendar(calendar)
                .dietLog(dietLog)
                .build();
        dietPictureRepository.save(dietPicture);
    }

    @Transactional
    public void deleteDietPicture(String email, String date, Long dietPictureId) {
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        DietPicture dietPicture = dietPictureRepository.findDietPictureByDietPictureId(dietPictureId)
                .orElseThrow(DietPictureNotFoundException::new);
        checkEqualsDiet(dietLog, dietPicture);
        dietPictureRepository.delete(dietPicture);
    }

    @Transactional
    private void checkEqualsDiet(DietLog dietLog, DietPicture dietPicture) {
        log.info("삭제 중");
        if (!(dietLog.getDietLogId() == dietPicture.getDietLog().getDietLogId())) {
            throw new DietListNotEqualsException();
        }
    }
}
