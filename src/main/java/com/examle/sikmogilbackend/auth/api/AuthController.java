package com.examle.sikmogilbackend.auth.api;

import com.examle.sikmogilbackend.auth.api.dto.request.RefreshTokenReqDto;
import com.examle.sikmogilbackend.auth.api.dto.request.TokenReqDto;
import com.examle.sikmogilbackend.auth.api.dto.response.MemberLoginResDto;
import com.examle.sikmogilbackend.auth.api.dto.response.UserInfo;
import com.examle.sikmogilbackend.auth.application.AuthMemberService;
import com.examle.sikmogilbackend.auth.application.AuthService;
import com.examle.sikmogilbackend.auth.application.AuthServiceFactory;
import com.examle.sikmogilbackend.auth.application.TokenService;
import com.examle.sikmogilbackend.global.jwt.api.dto.TokenDto;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.member.domain.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;
    private final TokenService tokenService;

    @PostMapping("/{provider}/token")
    public RspTemplate<TokenDto> generateAccessAndRefreshToken(
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto) {

        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(tokenReqDto.authCode());

        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo, SocialType.valueOf(provider.toUpperCase()));
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "토큰 발급", getToken);
    }

    @PostMapping("/token/access")
    public RspTemplate<TokenDto> generateAccessToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto) {
        TokenDto getToken = tokenService.generateAccessToken(refreshTokenReqDto);

        return new RspTemplate<>(HttpStatus.OK, "액세스 토큰 발급", getToken);
    }

}
