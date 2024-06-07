package com.examle.sikmogilbackend.member.domain;

import com.examle.sikmogilbackend.member.api.dto.reqeust.OnboardingInfoUpdateReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Schema(description = "멤버 id", example = "1")
    private Long memberId;

    @Schema(description = "최초 로그인 구분", example = "true, false")
    private boolean firstLogin;

    @Schema(description = "이메일", example = "abcd@gmail.com")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "사진 url", example = "url")
    private String picture;

    @Enumerated(value = EnumType.STRING)
    @Schema(description = "소셜로그인 타입", example = "GOOGLE, APPLE")
    private SocialType socialType;

    @Schema(description = "사용자가 설정한 닉네임", example = "홍홍홍")
    private String nickname;

    @Schema(description = "키", example = "180")
    private String height;

    @Schema(description = "몸무게", example = "90")
    private String weight;

    @Schema(description = "성별", example = "남자, 여자")
    private String gender;

    @Schema(description = "목표 몸무게", example = "75")
    private String targetWeight;

    @Schema(description = "목표 날짜", example = "2024.06.02")
    private String targetDate;

    @Schema(description = "생성 날짜", example = "2024.06.02")
    protected String createDate;

    @Enumerated(EnumType.STRING)
    @Schema(description = "권한", example = "ROLE_USER")
    private Role role;

    @Builder
    private Member(boolean firstLogin, String email, String name, String picture, SocialType socialType, Role role) {
        this.firstLogin = firstLogin;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.socialType = socialType;
        this.role = role;
    }

    public void onboardingUpdate(OnboardingInfoUpdateReqDto onboardingInfoUpdateReqDto) {
        this.nickname = onboardingInfoUpdateReqDto.nickname();
        this.height = onboardingInfoUpdateReqDto.height();
        this.weight = onboardingInfoUpdateReqDto.weight();
        this.gender = onboardingInfoUpdateReqDto.gender();
        this.targetWeight = onboardingInfoUpdateReqDto.targetWeight();
        this.targetDate = onboardingInfoUpdateReqDto.targetDate();
        this.createDate = onboardingInfoUpdateReqDto.createdDate();
    }

    public void firstLongUpdate() {
        this.firstLogin = false;
    }
}
