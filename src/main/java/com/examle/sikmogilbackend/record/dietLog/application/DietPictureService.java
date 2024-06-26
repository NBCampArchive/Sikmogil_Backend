package com.examle.sikmogilbackend.record.dietLog.application;

import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.Calendar.domain.repository.CalendarRepository;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.FindDietPictureDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        log.info("findDietPictureByDate ");
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        DietLog dietLog = dietLogService.findDietLogByDietDate(email, date);
        log.info("email = "+email);
        log.info("date = "+date);

        List<DietPicture> dietPictures = dietPictureRepository.findDietPictureByCalendarAndDietLog(calendar, dietLog);

        return dietPictures.stream()
                .map(DietPicture::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<DietPictureDTO> findDietPictureByDate (Calendar calendar) {
        log.info("findDietPictureByDate ");
        log.info("calendar = "+calendar);
        List<DietPicture> dietPictures = dietPictureRepository.findByCalendar(calendar);
        return dietPictures.stream()
                .map(DietPicture::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FindDietPictureDTO findDietPictures(String email, Integer page) {
        log.info("");
        log.info("email = "+email);
        Member member = memberService.findMember(email);
        PageRequest pageable = PageRequest.of(page, 10);
        Integer totalSize =  member.getCalendars().stream()
                            .flatMap(calendar -> calendar.getDietPictures().stream())
                            .collect(Collectors.toList()).size();
        List<DietPictureDTO> dietPictureDTOS = dietPictureRepository.findDietPicturesByEmail(email,pageable).stream()
                                                .map(DietPicture::toDTO)
                                                .collect(Collectors.toList());
        return FindDietPictureDTO.builder()
                .lastPage(totalSize/10)
                .pictures(dietPictureDTOS)
                .build();

    }

    @Transactional
    public void addDietPicture(String email, String date, DietPictureDTO dietPictureDTO){
        log.info("사진 추가 중");
        log.info("email = "+email);
        log.info("date = "+date);
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
        log.info("사진 삭제 중");
        log.info("email = "+email);
        log.info("date = "+date);
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
