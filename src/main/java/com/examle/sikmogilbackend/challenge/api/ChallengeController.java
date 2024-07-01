package com.examle.sikmogilbackend.challenge.api;

import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeSaveReqDto;
import com.examle.sikmogilbackend.challenge.application.ChallengeService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Operation(summary = "챌린지 그룹 참여", description = "챌린지 그룹을 참여 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("{challengeId}/join")
    public RspTemplate<String> joinChallenge(@AuthenticationPrincipal String email,
                                             @PathVariable Long challengeId) {
        challengeService.joinChallenge(email, challengeId);
        return new RspTemplate<>(HttpStatus.OK, "챌린지 참여", String.format("%d번 챌린지 참여!", challengeId));
    }

}
