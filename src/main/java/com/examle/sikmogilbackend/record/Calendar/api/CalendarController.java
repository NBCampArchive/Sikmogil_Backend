package com.examle.sikmogilbackend.record.Calendar.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import com.examle.sikmogilbackend.record.Calendar.api.dto.CalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.application.CalendarService;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.application.DietPictureService;
import com.examle.sikmogilbackend.record.dietLog.domain.DietPicture;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
    private final DietPictureService dietPictureService;

    @Operation(summary = "사용자의 캘린더 내역 출력", description = "사용자의 모든 날짜의 캘린더 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("")
    public List<CalendarDTO> findByMemberIdCalendar(Authentication authentication){
        return calendarService.findByMemberIdCalendar(authentication.getName());
    }

    @Operation(summary = "캘린더의 특정 날짜 데이터 출력", description = "특정 날짜의 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더의 특정 날짜 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/getCalendarDate")
    public CalendarDTO findCalendarByDiaryDate(Authentication authentication, String diaryDate){
        Calendar calendar = calendarService.findCalendarByDiaryDate(authentication.getName(), diaryDate);
        List<DietPictureDTO> dietPictureDTOS = dietPictureService.findDietPictureByDate(calendar);
        CalendarDTO calendarDTO = calendar.toDTO(dietPictureDTOS);
        return calendarDTO;
    }

    @Operation(summary = "특정 날짜의 캘린더 내용 업데이트", description = "특정 날짜의 캘린더 내용을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 데이터 입력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/UpdateCalendar")
    public RspTemplate<String> updateCalendar(Authentication authentication, CalendarDTO calendar){
        calendarService.UpdateCalendar(authentication.getName(), calendar);
        return new RspTemplate<>(HttpStatus.OK, "온보딩");
    }
}
