package com.examle.sikmogilbackend.record.WorkoutLog.application;

import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutLogDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.repository.WorkoutListRepository;
import com.examle.sikmogilbackend.record.WorkoutLog.exception.WorkoutListNotEqualsException;
import com.examle.sikmogilbackend.record.WorkoutLog.exception.WorkoutListNotFoundException;
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
public class WorkoutListService {
    private final WorkoutListRepository workoutListRepository;
    private final CalendarService calendarService;
    private final WorkoutLogService workoutLogService;

    private final MemberService memberService;

    @Transactional
    public List<WorkoutListDTO> findWorkoutListByDate (String email, String date) {
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        WorkoutLog workoutLog = workoutLogService.findWorkoutLogByWorkoutDate(email, date);

        List<WorkoutList> workoutLists = workoutListRepository.findWorkoutListsByCalendarAndWorkoutLog(calendar, workoutLog);

        return workoutLists.stream()
                .map(WorkoutList::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<WorkoutListDTO> findWorkoutListByDate (Calendar calendar) {
        List<WorkoutList> workoutLists = workoutListRepository.findWorkoutListsByCalendar(calendar);
        return workoutLists.stream()
                .map(WorkoutList::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<WorkoutList> findWorkoutPictures(String email) {
        Member member = memberService.findMember(email);
        return member.getCalendars().stream()
                .flatMap(calendar -> calendar.getWorkoutLists().stream())
                .filter(workoutList -> workoutList.getWorkoutPicture() != null)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addWorkoutList(String email, String date, WorkoutListDTO workoutListDTO){
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        WorkoutLog workoutLog = workoutLogService.findWorkoutLogByWorkoutDate(email, date);
        WorkoutList workoutList = WorkoutList.builder()
                .performedWorkout(workoutListDTO.performedWorkout())
                .workoutTime(workoutListDTO.workoutTime())
                .workoutIntensity(workoutListDTO.workoutIntensity())
                .workoutPicture(workoutListDTO.workoutPicture())
                .calorieBurned(workoutListDTO.calorieBurned())
                .calendar(calendar)
                .workoutLog(workoutLog)
                .build();
        workoutListRepository.save(workoutList);
    }

    @Transactional
    public void deleteWorkoutList(String email, String date, Long workoutListId) {
        WorkoutLog workoutLog = workoutLogService.findWorkoutLogByWorkoutDate(email, date);
        WorkoutList workoutList = workoutListRepository.findWorkoutListByWorkoutListId(workoutListId)
                .orElseThrow(WorkoutListNotFoundException::new);
        checkEqualsWorkout(workoutLog, workoutList);
        workoutListRepository.delete(workoutList);
    }

    @Transactional
    private void checkEqualsWorkout(WorkoutLog workoutLog, WorkoutList workoutList) {
        log.info("삭제 중");
        if (!(workoutLog.getWorkoutLogId() == workoutList.getWorkoutLog().getWorkoutLogId())) {
            throw new WorkoutListNotEqualsException();
        }
    }
}
