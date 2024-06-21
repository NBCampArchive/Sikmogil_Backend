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
        log.info("findByMemberIdDietLog 사진 찾기 중");
        log.info("email = "+email);
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<DietLog> dietLogs =
                dietLogRepository.findByMember(member);

        return dietLogs.stream()
                .map(DietLog::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DietLog findDietLogByDietDate (String email, String dietDate) {
        log.info("findDietLogByDietDate 사진 찾기 중");
        log.info("email = "+email);
        log.info("dietDate = "+dietDate);
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        DietLog dietLog = checkExistenceDietLog(member, dietDate);

        return dietLog;
    }

    @Transactional
    public void updateDietLog (String email, DietLogDTO dietLogDTO) {
        log.info("updateDietLog 사진 찾기 중");
        log.info("email = "+email);
        log.info("dietLogDTO = "+dietLogDTO.toString());
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        DietLog dietLog = checkExistenceDietLog(member, dietLogDTO.dietDate());

        dietLog.updateDietLog(dietLogDTO);
    }

    @Transactional
    public void createDietLog (Member member, String dietDate) {
        log.info("createDietLog 사진 찾기 중");
        log.info("member = "+member);
        log.info("dietDate = "+dietDate);
        log.info("캘린더 생성@@@@@@@@@@@@@");
        dietLogRepository.save(
                DietLog.builder()
                        .member(member)
                        .dietDate(dietDate)
                        .build());
    }

    @Transactional
    private DietLog checkExistenceDietLog(Member member, String dietDate) {
        log.info("checkExistenceDietLog 사진 찾기 중");
        log.info("member = "+member);
        log.info("dietDate = "+dietDate);
        try {
            if (!dietLogRepository.existsDietLogByMemberAndDietDate(member, dietDate))
                throw new DietLogNotFoundException();
        } catch (DietLogNotFoundException e) {
            log.error("Error = "+e.getMessage());
            log.error("dietDate"+dietDate);
            createDietLog(member, dietDate);
        }
        return dietLogRepository.findByMemberAndAndDietDate(member, dietDate);
    }
}
