package com.examle.sikmogilbackend.record.WorkoutLog.application;

import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.FindWorkoutPictureDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.repository.WorkoutListRepository;
import com.examle.sikmogilbackend.record.WorkoutLog.exception.WorkoutListNotEqualsException;
import com.examle.sikmogilbackend.record.WorkoutLog.exception.WorkoutListNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<WorkoutListDTO> findWorkoutListByDate(String email, String date) {
        log.info("findWorkoutListByDate ");
        log.info("email = " + email);
        log.info("date = " + date);
        Calendar calendar = calendarService.findCalendarByDiaryDate(email, date);
        WorkoutLog workoutLog = workoutLogService.findWorkoutLogByWorkoutDate(email, date);

        List<WorkoutList> workoutLists = workoutListRepository.findWorkoutListsByCalendarAndWorkoutLog(calendar,
                workoutLog);

        return workoutLists.stream()
                .map(WorkoutList::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<WorkoutListDTO> findWorkoutListByDate(Calendar calendar) {
        log.info("findWorkoutListByDate ");
        log.info("calendar = " + calendar);
        List<WorkoutList> workoutLists = workoutListRepository.findWorkoutListsByCalendar(calendar);
        return workoutLists.stream()
                .map(WorkoutList::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FindWorkoutPictureDTO findWorkoutPictures(String email, Integer page) {
        log.info("findWorkoutPictures ");
        log.info("email = " + email);
        Member member = memberService.findMember(email);
        Integer total = member.getCalendars().stream()
                .flatMap(calendar -> calendar.getWorkoutLists().stream())
                .filter(workoutList -> workoutList.getWorkoutPicture() != null)
                .collect(Collectors.toList()).size();
        PageRequest pageable = PageRequest.of(page, 10);
        List<WorkoutListDTO> workoutListDTOS = workoutListRepository.findWorkoutListByByEmailAndWorkoutPictureNotNull(
                        email, pageable).stream()
                .map(WorkoutList::toDTO)
                .collect(Collectors.toList());
        return FindWorkoutPictureDTO.builder()
                .lastPage(total / 10)
                .pictures(workoutListDTOS)
                .build();
    }

    @Transactional
    public void addWorkoutList(String email, String date, WorkoutListDTO workoutListDTO) {
        log.info("사진 추가 중");
        log.info("email = " + email);
        log.info("date = " + date);
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
        log.info("사진 삭제 중");
        log.info("email = " + email);
        log.info("date = " + date);
        WorkoutLog workoutLog = workoutLogService.findWorkoutLogByWorkoutDate(email, date);
        WorkoutList workoutList = workoutListRepository.findWorkoutListByWorkoutListId(workoutListId)
                .orElseThrow(WorkoutListNotFoundException::new);
        checkEqualsWorkout(workoutLog, workoutList);
        workoutListRepository.delete(workoutList);
    }

    // workoutList의 사진만 삭제!
    @Transactional
    public void deleteOnlyWorkoutListPicture(String email, String date, Long workoutListId) {
        WorkoutLog workoutLog = workoutLogService.findWorkoutLogByWorkoutDate(email, date);
        WorkoutList workoutList = workoutListRepository.findWorkoutListByWorkoutListId(workoutListId)
                .orElseThrow(WorkoutListNotFoundException::new);
        checkEqualsWorkout(workoutLog, workoutList);
        workoutList.deleteWorkoutPicture();
    }

    @Transactional
    private void checkEqualsWorkout(WorkoutLog workoutLog, WorkoutList workoutList) {
        log.info("삭제 중");
        if (!(workoutLog.getWorkoutLogId() == workoutList.getWorkoutLog().getWorkoutLogId())) {
            throw new WorkoutListNotEqualsException();
        }
    }
}
