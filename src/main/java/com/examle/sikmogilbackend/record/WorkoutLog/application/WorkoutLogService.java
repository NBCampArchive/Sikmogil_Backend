package com.examle.sikmogilbackend.record.WorkoutLog.application;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutLogDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.repository.WorkoutLogRepository;
import com.examle.sikmogilbackend.record.WorkoutLog.exception.WorkoutLogNotFoundException;
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
public class WorkoutLogService {
    private final WorkoutLogRepository workoutLogRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public List<WorkoutLog> findByMemberIdWorkoutLog (String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<WorkoutLog> workoutLogs =
                workoutLogRepository.findByMember(member);

        return workoutLogs;
    }

    @Transactional
    public WorkoutLog findWorkoutLogByWorkoutDate (String email, String workoutDate) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        WorkoutLog workoutLog = checkExistenceWorkoutLog(member, workoutDate);

        return workoutLog;
    }

    @Transactional
    public void updateWorkoutLog (String email, WorkoutLogDTO workoutLogDTO) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        WorkoutLog workoutLog = checkExistenceWorkoutLog(member, workoutLogDTO.workoutDate());
        log.info("@@@@@@ = "+workoutLogDTO.toString());
        workoutLog.updateWorkoutLog(workoutLogDTO);
    }

    @Transactional
    public void createWorkoutLog (Member member, String workoutDate) {
        log.info("캘린더 생성@@@@@@@@@@@@@");
        workoutLogRepository.save(
                WorkoutLog.builder()
                        .member(member)
                        .workoutDate(workoutDate)
                        .build());
    }

    @Transactional
    private WorkoutLog checkExistenceWorkoutLog(Member member, String workoutDate) {
        try {
            if (!workoutLogRepository.existsCalendarByMemberAndWorkoutDate(member, workoutDate))
                throw new WorkoutLogNotFoundException();
        } catch (WorkoutLogNotFoundException e) {
            log.error("Error = "+e.getMessage());
            log.error("workoutDate"+workoutDate);
            createWorkoutLog(member, workoutDate);
        }
        return workoutLogRepository.findByMemberAndAndWorkoutDate(member, workoutDate);
    }
}
