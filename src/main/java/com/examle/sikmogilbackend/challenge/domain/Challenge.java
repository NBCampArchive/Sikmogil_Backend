package com.examle.sikmogilbackend.challenge.domain;

import com.examle.sikmogilbackend.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    @Schema(description = "챌린지 그룹아이디")
    private Long challengeId;

    @OneToOne
    @Schema(description = "챌린지 그룹장")
    private Member leader;

    @Schema(description = "챌린지 그룹 주제")
    private String topic;

    @Schema(description = "챌린지 그룹 이름")
    private String name;

    @Schema(description = "챌린지 그룹 소개")
    private String introduction;

    @Schema(description = "챌린지 그룹 성별")
    private String gender;

    @Schema(description = "챌린지 그룹 활동 정도")
    private String activityLevel;

    @OneToMany(mappedBy = "challenge", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChallengeMember> challengeMembers = new ArrayList<>();

    @Builder
    private Challenge(Member leader, String topic, String name, String introduction, String gender,
                      String activityLevel) {
        this.leader = leader;
        this.topic = topic;
        this.name = name;
        this.introduction = introduction;
        this.gender = gender;
        this.activityLevel = activityLevel;
    }

}
