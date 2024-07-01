package com.examle.sikmogilbackend.challenge.api;

import com.examle.sikmogilbackend.challenge.api.dto.request.ChallengeSaveReqDto;
import com.examle.sikmogilbackend.challenge.application.ChallengeService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping("/")
    public RspTemplate<String> challengeSave(@AuthenticationPrincipal String email,
                                             @RequestBody ChallengeSaveReqDto challengeSaveReqDto) {
        Long challengeId = challengeService.challengeSave(email, challengeSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "챌린지 그룹 생성", String.format("%d번 챌린지 그룹 생성!", challengeId));
    }
    
}
