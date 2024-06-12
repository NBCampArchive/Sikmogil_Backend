package com.examle.sikmogilbackend.record.Calendar.application;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.ExistsNickNameException;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import com.examle.sikmogilbackend.record.Calendar.api.dto.CalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.Calendar.domain.repository.CalendarRepository;
import com.examle.sikmogilbackend.record.Calendar.exception.CalendarNotFoundException;
import com.examle.sikmogilbackend.record.Calendar.exception.ExistsCalendarException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<CalendarDTO> findByMemberIdCalendar (String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Calendar> calendars =
                calendarRepository.findByMember(member);

        return calendars.stream()
                .map(Calendar::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MainCalendarDTO searchWeekWeightAndTargetWeight (String email, String date) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Calendar> calendars =
                calendarRepository.findByMember(member);
        List<WeekWeightDTO> weekWeights = calendars.stream()
                .map(Calendar::toWeekDTO)
                .collect(Collectors.toList());

        return MainCalendarDTO.builder()
                .targetDate(member.getTargetDate())
                .targetWeight(member.getTargetWeight())
                .weekWeights(weekWeights)
                .build();
    }

    @Transactional
    public Calendar findCalendarByDiaryDate (String email, String diaryDate) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Calendar calendar = checkExistenceCalendar(member, diaryDate);

        return calendar;
    }

    @Transactional
    public void UpdateCalendar (String email, CalendarDTO calendarDTO) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Calendar calendar = checkExistenceCalendar(member, calendarDTO.diaryDate());

        calendar.updateCalendar(calendarDTO);
    }

    @Transactional
    public void createCalendar (Member member, String diaryDate) {
        log.info("캘린더 생성@@@@@@@@@@@@@");
        calendarRepository.save(
                Calendar.builder()
                        .member(member)
                        .diaryDate(diaryDate)
                        .build());
    }

    private Calendar checkExistenceCalendar(Member member, String diaryDate) {
        try {
            if (!calendarRepository.existsCalendarByMemberAndDiaryDate(member, diaryDate))
                throw new CalendarNotFoundException();
        } catch (CalendarNotFoundException e) {
            log.error("Error = "+e.getMessage());
            createCalendar(member, diaryDate);
        }
        return calendarRepository.findByMemberAndAndDiaryDate(member, diaryDate);
    }
}
