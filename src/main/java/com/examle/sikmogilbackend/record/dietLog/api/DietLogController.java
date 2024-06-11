package com.examle.sikmogilbackend.record.dietLog.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.application.DietListService;
import com.examle.sikmogilbackend.record.dietLog.application.DietLogService;
import com.examle.sikmogilbackend.record.dietLog.application.DietPictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dietLog")
@RequiredArgsConstructor
public class DietLogController {
    private final DietLogService dietLogService;
    private final DietListService dietListService;
    private final DietPictureService dietPictureService;

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
    public DietLogDTO findDietLogByDietDate(Authentication authentication,
                                            @RequestBody String dietDate){
        return dietLogService.findDietLogByDietDate(authentication.getName(), dietDate).toDTO();
    }

    @Operation(summary = "특정 날짜의 식단 내용 업데이트", description = "특정 날짜의 식단을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 데이터 입력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/updateDietLog")
    public RspTemplate<String> updateDietLog(Authentication authentication,
                                             @RequestBody DietLogDTO dietLog){
        dietLogService.updateDietLog(authentication.getName(), dietLog);
        return new RspTemplate<>(HttpStatus.OK, "식단 업데이트 성공");
    }

//    dietList - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    @Operation(summary = "특정 날짜의 식단 리스트 출력", description = "특정 날짜의 식단 리스트를 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 날짜의 식단 리스트 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/dietList/getDietListByDate")
    public List<DietListDTO> findFoodListByDate(Authentication authentication,
                                                @RequestBody String date){
        return dietListService.findDietListByDate(authentication.getName(), date);
    }

    @Operation(summary = "특정 날짜의 식단 추가", description = "특정 날짜의 식단 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/dietList/addDietList")
    public RspTemplate<String> addDietList(Authentication authentication,
                                           @RequestBody String date,
                                           @RequestBody DietListDTO dietList){
        dietListService.addDietList(authentication.getName(),date,dietList);
        return new RspTemplate<>(HttpStatus.OK, "식단 추가 성공");
    }

    @Operation(summary = "특정 날짜의 식단 삭제", description = "특정 날짜의 식단을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/dietList/deleteDietList")
    public RspTemplate<String> deleteDietList(Authentication authentication,
                                              @RequestBody String date,
                                              @RequestBody Long dietListId){
        dietListService.deleteDietList(authentication.getName(),date,dietListId);
        return new RspTemplate<>(HttpStatus.OK, "식단 삭제 성공");
    }

// DietPicture - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    @Operation(summary = "특정 날짜의 식단 사진 출력", description = "특정 날짜의 식단 사진을 모두 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 날짜의 식단 사진 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
    })
    @GetMapping("/dietPicture/getDietPictureByDate")
    public List<DietPictureDTO> findDietPictureByDate(Authentication authentication,
                                                      @RequestBody String date){
        return dietPictureService.findDietPictureByDate(authentication.getName(), date);
    }

    @Operation(summary = "특정 날짜의 식단 사진 추가", description = "특정 날짜의 식단 사진을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 사진 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/dietPicture/addDietPicture")
    public RspTemplate<String> addDietPicture(Authentication authentication,
                                              @RequestBody String date,
                                              @RequestBody DietPictureDTO dietPictureDTO) {
        dietPictureService.addDietPicture(authentication.getName(),date, dietPictureDTO);
        return new RspTemplate<>(HttpStatus.OK, "식단 사진 추가 성공");
    }

    @Operation(summary = "특정 날짜의 식단 사진 삭제", description = "특정 날짜의 식단 사진을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식단 삭제 사진 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/dietPicture/deleteDietPicture")
    public RspTemplate<String> deleteDietPicture(Authentication authentication,
                                                 @RequestBody String date,
                                                 @RequestBody Long dietPictureId){
        dietPictureService.deleteDietPicture(authentication.getName(),date,dietPictureId);
        return new RspTemplate<>(HttpStatus.OK, "식단 삭제 사진 성공");
    }

}
