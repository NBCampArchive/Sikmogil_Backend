package com.examle.sikmogilbackend.record.dietLog.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogDTO;
import com.examle.sikmogilbackend.record.dietLog.application.DietLogService;
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

@Slf4j
@RestController
@RequestMapping("/api/dietLog")
@RequiredArgsConstructor
public class DietLogController {
    private final DietLogService dietLogService;

    @Operation(summary = "사용자의 모든 식단 내역 출력", description = "사용자의 모든 식단 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("")
    public List<DietLogDTO> findByMemberIdDietLog(Authentication authentication){
        return dietLogService.findByMemberIdDietLog(authentication.getName());
    }

    @Operation(summary = "식단의 특정 날짜 데이터 출력", description = "특정 날짜의 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단의 특정 날짜 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/getDietLogDate")
    public DietLogDTO findDietLogByDietDate(Authentication authentication, String dietDate){
        return dietLogService.findDietLogByDietDate(authentication.getName(), dietDate);
    }

    @Operation(summary = "특정 날짜의 식단 내용 업데이트", description = "특정 날짜의 식단을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 데이터 입력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/updateDietLog")
    public RspTemplate<String> updateDietLog(Authentication authentication, DietLogDTO dietLog){
        dietLogService.updateDietLog(authentication.getName(), dietLog);
        return new RspTemplate<>(HttpStatus.OK, "식단 업데이트 성공");
    }
}
