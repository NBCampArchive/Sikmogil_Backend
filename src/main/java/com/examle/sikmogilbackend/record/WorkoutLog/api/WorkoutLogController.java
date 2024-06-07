package com.examle.sikmogilbackend.record.WorkoutLog.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutLogDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.application.WorkoutLogService;
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
@RequestMapping("/api/workoutLog")
@RequiredArgsConstructor
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;

    @Operation(summary = "사용자의 모든 운동 내역 출력", description = "사용자의 모든 운동 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("")
    public List<WorkoutLogDTO> findByMemberIdWorkoutLog(Authentication authentication){
        return workoutLogService.findByMemberIdWorkoutLog(authentication.getName());
    }

    @Operation(summary = "운동의 특정 날짜 데이터 출력", description = "특정 날짜의 내용을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동의 특정 날짜 데이터 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/getWorkoutLogDate")
    public WorkoutLogDTO findWorkoutLogByWorkoutDate(Authentication authentication, String workoutDate){
        return workoutLogService.findWorkoutLogByWorkoutDate(authentication.getName(), workoutDate);
    }

    @Operation(summary = "특정 날짜의 운동 내용 업데이트", description = "특정 날짜의 운동을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 데이터 입력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/updateWorkoutLog")
    public RspTemplate<String> updateWorkoutLog(Authentication authentication, WorkoutLogDTO workoutLog){
        workoutLogService.updateWorkoutLog(authentication.getName(), workoutLog);
        return new RspTemplate<>(HttpStatus.OK, "운동 업데이트 성공");
    }
}
