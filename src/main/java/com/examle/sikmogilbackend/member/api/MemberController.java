package com.examle.sikmogilbackend.member.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.member.api.dto.reqeust.OnboardingInfoUpdateReqDto;
import com.examle.sikmogilbackend.member.application.MemberService;
import com.examle.sikmogilbackend.member.exception.ExistsNickNameException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "로그인 성공(최초 로그인 구별)", description = "로그인 시에 불러올 api (true: 최초 로그인 O, false: 최초 로그인 X ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/success")
    public RspTemplate<Boolean> isFirstLogin(@AuthenticationPrincipal String email) {
        return new RspTemplate<>(HttpStatus.OK, "최초 로그인 여부", memberService.memberFirstLogin(email));
    }

    @Operation(summary = "온보딩 정보 업데이트", description = "로그인 후 온보딩 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "온보딩 정보 입력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/onboarding")
    public RspTemplate<String> onboardingInfoUpdate(@AuthenticationPrincipal String email,
                                                    @RequestBody OnboardingInfoUpdateReqDto onboardingInfoUpdateReqDto) {
        memberService.onboardingInfoUpdate(email, onboardingInfoUpdateReqDto);
        return new RspTemplate<>(HttpStatus.OK, "온보딩");
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임이 중복인지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능 닉네임"),
            @ApiResponse(responseCode = "400", description = "중복 닉네임 입니다."),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/nickname")
    public RspTemplate<Boolean> duplicateNickName(@RequestParam(name = "nickname") String nickName) {
        try {
            memberService.validateDuplicateNickName(nickName);
            return new RspTemplate<>(HttpStatus.OK, "사용 가능한 닉네임입니다.", true);
        } catch (ExistsNickNameException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, e.getMessage(), false);
        }
    }

    @Operation(summary = "온보딩 정보 출력", description = "온보딩 정보를 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "온보딩 정보 출력 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/getMember")
    public RspTemplate<OnboardingInfoUpdateReqDto> findMember(@AuthenticationPrincipal String email) {
        return new RspTemplate<>(HttpStatus.OK, "온보딩 정보 출력", memberService.findMember(email).toDTO());
    }
    
}
