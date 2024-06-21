package com.examle.sikmogilbackend.record.dietLog.application;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogDTO;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import com.examle.sikmogilbackend.record.dietLog.domain.repository.DietLogRepository;
import com.examle.sikmogilbackend.record.dietLog.exception.DietLogNotFoundException;
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
public class DietLogService {
    private final DietLogRepository dietLogRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public List<DietLogDTO> findByMemberIdDietLog (String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<DietLog> dietLogs =
                dietLogRepository.findByMember(member);

        return dietLogs.stream()
                .map(DietLog::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DietLog findDietLogByDietDate (String email, String dietDate) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        DietLog dietLog = checkExistenceDietLog(member, dietDate);

        return dietLog;
    }

    @Transactional
    public void updateDietLog (String email, DietLogDTO dietLogDTO) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        DietLog dietLog = checkExistenceDietLog(member, dietLogDTO.dietDate());

        dietLog.updateDietLog(dietLogDTO);
    }

    @Transactional
    public void createDietLog (Member member, String dietDate) {
        log.info("캘린더 생성@@@@@@@@@@@@@");
        dietLogRepository.save(
                DietLog.builder()
                        .member(member)
                        .dietDate(dietDate)
                        .build());
    }

    @Transactional
    private DietLog checkExistenceDietLog(Member member, String dietDate) {
        try {
            if (!dietLogRepository.existsDietLogByMemberAndDietDate(member, dietDate))
                throw new DietLogNotFoundException();
        } catch (DietLogNotFoundException e) {
            log.error("Error = "+e.getMessage());
            createDietLog(member, dietDate);
        }
        return dietLogRepository.findByMemberAndAndDietDate(member, dietDate);
    }
}
