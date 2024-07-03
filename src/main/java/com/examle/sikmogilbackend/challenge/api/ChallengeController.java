package com.examle.sikmogilbackend.challenge.api;

import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeSaveReqDto;
import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeUpdateReqDto;
import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeInfoResDto;
import com.examle.sikmogilbackend.challenge.api.dto.response.ChallengeListResDto;
import com.examle.sikmogilbackend.challenge.application.ChallengeService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.global.util.PageableUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 그룹 등록", description = "챌린지 그룹을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/")
    public RspTemplate<String> challengeSave(@AuthenticationPrincipal String email,
                                             @RequestBody ChallengeSaveReqDto challengeSaveReqDto) {
        Long challengeId = challengeService.challengeSave(email, challengeSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "챌린지 그룹 생성", String.format("%d번 챌린지 그룹 생성!", challengeId));
    }

    @Operation(summary = "챌린지 그룹 수정", description = "챌린지 그룹 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PutMapping("/{challengeId}")
    public RspTemplate<ChallengeInfoResDto> challengeUpdate(@AuthenticationPrincipal String email,
                                                            @PathVariable Long challengeId,
                                                            @RequestBody ChallengeUpdateReqDto challengeUpdateReqDto) {
        return new RspTemplate<>(HttpStatus.OK, "챌린지 그룹 수정",
                challengeService.challengeUpdate(email, challengeId, challengeUpdateReqDto));
    }

    @Operation(summary = "챌린지 그룹 삭제", description = "챌린지 그룹을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @DeleteMapping("/{challengeId}")
    public RspTemplate<Void> challengeDelete(@AuthenticationPrincipal String email,
                                             @PathVariable Long challengeId) {
        challengeService.challengeDelete(email, challengeId);
        return new RspTemplate<>(HttpStatus.OK, "챌린지 그룹 삭제");
    }

    // 챌린지 그룹 리스트
    @Operation(summary = "주제별 챌린지 그룹 목록 조회",
            description = "주제별 챌린지 그룹 목록을 조회 합니다. (해당 api에서는  챌린지 그룹장의 정보가 필요없기에 null로 표시.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/{topic}")
    public RspTemplate<ChallengeListResDto> topicByChallengeAll(@AuthenticationPrincipal String email,
                                                                @Parameter(name = "topic", description = "챌린지 그룹 주제(다이어트, 운동, 습관형성) 이와같이 작성해야함.", in = ParameterIn.PATH)
                                                                @PathVariable String topic,
                                                                @Parameter(name = "page", description = "게시물 page", in = ParameterIn.QUERY)
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @Parameter(name = "size", description = "게시물 page size", in = ParameterIn.QUERY)
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageableUtil.of(page, size);
        ChallengeListResDto challenges = challengeService.topicByChallengeAll(email, topic, pageable);
        return new RspTemplate<>(HttpStatus.OK, "주제별 챌린지 그룹 조회", challenges);
    }

    @Operation(summary = "챌린지 그룹 상세보기", description = "챌린지 그룹을 상세 봅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세보기 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/detail/{challengeId}")
    public RspTemplate<ChallengeInfoResDto> challengeDetail(@AuthenticationPrincipal String email,
                                                            @PathVariable Long challengeId) {
        return new RspTemplate<>(HttpStatus.OK, "챌린지 그룹 상세보기", challengeService.challengeDetail(email, challengeId));
    }

    @Operation(summary = "챌린지 그룹 참여", description = "챌린지 그룹을 참여 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/{challengeId}/join")
    public RspTemplate<String> joinChallenge(@AuthenticationPrincipal String email,
                                             @PathVariable Long challengeId) {
        challengeService.joinChallenge(email, challengeId);
        return new RspTemplate<>(HttpStatus.OK, "챌린지 그룹 참여", String.format("%d번 챌린지 참여!", challengeId));
    }

    @Operation(summary = "참여자 챌린지 그룹 탈퇴", description = "참여자가 챌린지 그룹을 탈퇴 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/{challengeId}/unjoin")
    public RspTemplate<Void> unJoinChallenge(@AuthenticationPrincipal String email,
                                             @PathVariable Long challengeId) {
        challengeService.unJoinChallenge(email, challengeId);
        return new RspTemplate<>(HttpStatus.OK, "참여자 챌린지 그룹 탈퇴");
    }
}
