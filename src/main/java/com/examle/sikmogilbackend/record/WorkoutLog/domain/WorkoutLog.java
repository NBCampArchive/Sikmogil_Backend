package com.examle.sikmogilbackend.record.WorkoutLog.domain;

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
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_log_id")
    @Schema(description = "운동 기록 id", example = "1")
    private Long workoutLogId;

    @Schema(description = "운동 날짜", example = "2024.06.02")
    protected String workoutDate;

    @Schema(description = "걸음 수", example = "100")
    protected Long steps;

    @Schema(description = "총 소모 칼로리", example = "100")
    protected Long totalCaloriesBurned;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "workoutLog", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalendarWorkoutLog> calendarDietLogs = new ArrayList<>();
}