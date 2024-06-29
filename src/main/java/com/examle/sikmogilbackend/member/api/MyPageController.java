package com.examle.sikmogilbackend.member.api;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.member.application.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class MyPageController {
    private final MyPageService myPageService;
    
    @PostMapping("/delete-account")
    public RspTemplate<String> memberDeleteAccount(@AuthenticationPrincipal String email) {
        myPageService.memberDeleteAccount(email);

        return new RspTemplate<>(HttpStatus.OK, "회원 탈퇴", String.format("이메일: %s님 회원 탈퇴", email));
    }
}
