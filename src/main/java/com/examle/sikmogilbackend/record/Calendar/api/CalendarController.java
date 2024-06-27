package com.examle.sikmogilbackend.record.Calendar.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.api.dto.CalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.api.dto.FindCalendarByDateDTO;
import com.examle.sikmogilbackend.record.Calendar.api.dto.MainCalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.application.WorkoutListService;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogInPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.application.DietLogService;
import com.examle.sikmogilbackend.record.dietLog.application.DietPictureService;
import com.examle.sikmogilbackend.record.dietLog.domain.DietLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(summary = "사용자의 캘린더 내역 출력", description = "사용자의 모든 날짜의 캘린더 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("")
    public List<CalendarDTO> findByMemberIdCalendar(Authentication authentication){
        return calendarService.findByMemberIdCalendar(authentication.getName());
    }

    @Operation(summary = "사용자의 캘린더 내역 출력", description = "사용자의 모든 날짜의 캘린더 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/getWeek")
    public MainCalendarDTO searchWeekWeightAndTargetWeight(Authentication authentication){
        return calendarService.searchWeekWeightAndTargetWeight(authentication.getName());
    }


    @Operation(summary = "캘린더의 특정 날짜 데이터 출력", description = "특정 날짜의 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더의 특정 날짜 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/getCalendarDate")
    public FindCalendarByDateDTO findCalendarByDiaryDate(Authentication authentication,
                                                         @RequestParam String diaryDate){
        log.info("getCalendarDate 끝");
        return calendarService.findCalendarByDateInDietLogAndWorkoutList(authentication.getName(), diaryDate);
    }

    @Operation(summary = "특정 날짜의 캘린더 내용 업데이트", description = "특정 날짜의 캘린더 내용을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 데이터 입력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/updateCalendar")
    public RspTemplate<String> updateCalendar(Authentication authentication,
                                              @RequestBody CalendarDTO calendar){
        calendarService.UpdateCalendar(authentication.getName(), calendar);
        return new RspTemplate<>(HttpStatus.OK, "캘린더 데이터 입력 성공");
    }

    @Operation(summary = "특정 날짜의 몸무게 업데이트", description = "특정 날짜의 몸무게를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 날짜의 몸무게 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/updateWeight")
    public RspTemplate<String> updateWeight(Authentication authentication,
                                              @RequestParam String date,
                                              @RequestParam Long weight){
        calendarService.updateWeight(authentication.getName(), date, weight);
        return new RspTemplate<>(HttpStatus.OK, "특정 날짜의 몸무게 저장 성공");
    }
}
