package com.examle.sikmogilbackend.record.dietLog.domain;

import com.examle.sikmogilbackend.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_log_id")
    @Schema(description = "식단 기록 id", example = "1")
    private Long dietLogId;

    @Schema(description = "해당 날짜의 물 섭취량", example = "100")
    protected Long waterIntake;

    @Schema(description = "해당 날짜의 총 먹은 칼로리", example = "100")
    protected Long totalCalorieEaten;

    @Schema(description = "먹은 날짜", example = "2024.06.02")
    protected String dietDate;

    @OneToMany(mappedBy = "dietLog", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalendarDietLog> calendarDietLogs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}