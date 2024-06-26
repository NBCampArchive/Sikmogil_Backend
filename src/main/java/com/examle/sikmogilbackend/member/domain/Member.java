package com.examle.sikmogilbackend.member.domain;

import com.examle.sikmogilbackend.member.api.dto.reqeust.OnboardingInfoUpdateReqDto;
import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    private static final String NOT_KNOWN = "(알수없음)";

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

    @Schema(description = "먹을 수 있는 칼로리", example = "1000")
    private Long canEatCalorie;

    @Schema(description = "생성 날짜", example = "2024.06.02")
    protected String createDate;

    @Schema(description = "알림 시간", example = "22:00")
    protected String remindTime;

    @Enumerated(EnumType.STRING)
    @Schema(description = "권한", example = "ROLE_USER")
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Calendar> calendars = new ArrayList<>();

    @Schema(description = "회원 탈퇴 여부", example = "true: 탈퇴 O, false: 탈퇴 X")
    private boolean isDelete;

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
        this.picture = onboardingInfoUpdateReqDto.picture();
        this.height = onboardingInfoUpdateReqDto.height();
        this.weight = onboardingInfoUpdateReqDto.weight();
        this.gender = onboardingInfoUpdateReqDto.gender();
        this.targetWeight = onboardingInfoUpdateReqDto.targetWeight();
        this.targetDate = onboardingInfoUpdateReqDto.targetDate();
        this.createDate = onboardingInfoUpdateReqDto.createdDate();
        this.canEatCalorie = onboardingInfoUpdateReqDto.canEatCalorie();
        this.remindTime = onboardingInfoUpdateReqDto.remindTime();
    }

    public OnboardingInfoUpdateReqDto toDTO() {
        return OnboardingInfoUpdateReqDto.builder()
                .picture(picture)
                .nickname(nickname)
                .height(height)
                .weight(weight)
                .gender(gender)
                .targetDate(targetDate)
                .targetWeight(targetWeight)
                .createdDate(createDate)
                .canEatCalorie(canEatCalorie)
                .remindTime(remindTime)
                .build();
    }

    public void firstLongUpdate() {
        this.firstLogin = false;
    }

    public void deleteAccount() {
        this.isDelete = true;
        this.email = NOT_KNOWN;
        this.name = NOT_KNOWN;
        this.picture = NOT_KNOWN;
        this.nickname = NOT_KNOWN;
        this.gender = NOT_KNOWN;
        this.height = NOT_KNOWN;
        this.weight = NOT_KNOWN;
    }
}
