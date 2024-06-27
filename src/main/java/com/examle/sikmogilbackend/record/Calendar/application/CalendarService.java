package com.examle.sikmogilbackend.record.Calendar.application;

import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.ExistsNickNameException;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import com.examle.sikmogilbackend.record.Calendar.api.dto.CalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.api.dto.FindCalendarByDateDTO;
import com.examle.sikmogilbackend.record.Calendar.api.dto.MainCalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.api.dto.WeekWeightDTO;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.Calendar.domain.repository.CalendarRepository;
import com.examle.sikmogilbackend.record.Calendar.exception.CalendarNotFoundException;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogInPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.application.DietLogService;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    private final DietLogService dietLogService;

    private final MemberService memberService;

    @Transactional
    public List<CalendarDTO> findByMemberIdCalendar (String email) {
        log.info("findByMemberIdCalendar ");
        log.info("email = "+email);

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Calendar> calendars =
                calendarRepository.findByMember(member);

        return calendars.stream()
                .map(Calendar::toDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public MainCalendarDTO searchWeekWeightAndTargetWeight (String email) {
        log.info("searchWeekWeightAndTargetWeight ");
        log.info("email = "+email);

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Calendar> calendars =
                calendarRepository.findCalendarByDiaryWeightIsNotNullAndMember(member);
        List<WeekWeightDTO> weekWeights = calendars.stream()
                .map(Calendar::toWeekDTO)
                .collect(Collectors.toList());

        log.info("sort = "+weekWeights.toString());

        Collections.sort( weekWeights, (o1, o2) -> o2.date().compareTo(o1.date()));

        log.info("sort = "+weekWeights.toString());


        return MainCalendarDTO.builder()
                .createDate(member.getCreateDate())
                .targetDate(member.getTargetDate())
                .targetWeight(member.getTargetWeight())
                .weight(member.getWeight())
                .weekWeights(weekWeights.size() < 7?weekWeights:weekWeights.subList(0,7))
                .build();
    }

    @Transactional
    public FindCalendarByDateDTO findCalendarByDateInDietLogAndWorkoutList(String email, String diaryDate){
        Calendar calendar = findCalendarByDiaryDate(email, diaryDate);

        List<DietLog> dietLogs = dietLogService.findByMemberIdDietLogInPicture(email);


        List<DietLogInPictureDTO> dietLogInPictureDTOs =
                dietLogs.stream()
                        .map(DietLog::toPictureDTO)
                        .collect(Collectors.toList());

        Member member = memberService.findMember(email);
        FindCalendarByDateDTO findCalendarByDateDTO = FindCalendarByDateDTO.builder()
                .canEatCalorie(member.getCanEatCalorie())
                .diaryDate(calendar.getDiaryDate())
                .diaryWeight(calendar.getDiaryWeight())
                .diaryText(calendar.getDiaryText())
                .dietLogInPictureDTOS(dietLogInPictureDTOs)
                .workoutLists(
                        calendar.getWorkoutLists().stream()
                                .map(WorkoutList::toDTO)
                                .collect(Collectors.toList())
                )
                .build();
        return findCalendarByDateDTO;
    }

    @Transactional
    public Calendar findCalendarByDiaryDate (String email, String diaryDate) {
        log.info("findCalendarByDiaryDate ");
        log.info("email = "+email);
        log.info("diaryDate = "+diaryDate);
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Calendar calendar = checkExistenceCalendar(member, diaryDate);

        return calendar;
    }

    @Transactional
    public void UpdateCalendar (String email, CalendarDTO calendarDTO) {
        log.info("UpdateCalendar ");
        log.info("email = "+email);
        log.info("calendarDTO = "+calendarDTO.toString());

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Calendar calendar = checkExistenceCalendar(member, calendarDTO.diaryDate());

        calendar.updateCalendar(calendarDTO);
    }

    @Transactional
    public void updateWeight (String email, String date, Long weight) {
        log.info("updateWeight ");
        log.info("email = "+email);
        log.info("date = "+date);
        log.info("weight = "+weight);

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Calendar calendar = checkExistenceCalendar(member, date);

        calendar.updateWeight(weight);
    }

    @Transactional
    public void createCalendar (Member member, String diaryDate) {
        log.info("createCalendar ");
        log.info("member = "+member);
        log.info("diaryDate = "+diaryDate);
        if (calendarRepository.existsCalendarByMemberAndDiaryDate(member, diaryDate))
            return;
        log.info("캘린더 생성@@@@@@@@@@@@@");
        calendarRepository.save(
                Calendar.builder()
                        .member(member)
                        .diaryDate(diaryDate)
                        .build());
    }

    @Transactional
    private Calendar checkExistenceCalendar(Member member, String diaryDate) {
        log.info("checkExistenceCalendar ");
        log.info("member = "+member);
        log.info("diaryDate = "+diaryDate);

        try {
            if (!calendarRepository.existsCalendarByMemberAndDiaryDate(member, diaryDate))
                throw new CalendarNotFoundException();
        } catch (CalendarNotFoundException e) {
            log.error("Error = "+e.getMessage());
            log.error("diaryDate"+diaryDate);
            createCalendar(member, diaryDate);
        }
        return calendarRepository.findByMemberAndAndDiaryDate(member, diaryDate);
    }
}
