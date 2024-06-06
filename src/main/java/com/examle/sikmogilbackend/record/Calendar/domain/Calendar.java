package com.examle.sikmogilbackend.record.Calendar.domain;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.CalendarWorkoutLog;
import com.examle.sikmogilbackend.record.dietLog.domain.CalendarDietLog;
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
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    @Schema(description = "캘린더 id", example = "1")
    private Long calendarId;

    @Schema(description = "한 줄 일기", example = "오늘 하루 좋았다.")
    @Column(columnDefinition="TEXT")
    private String diaryText;

    @Schema(description = "해당 날짜의 체중", example = "오늘 하루 좋았다.")
    @Column(columnDefinition="TEXT")
    private Long diaryWeight;

    @Schema(description = "일기 날짜", example = "2024.06.02")
    protected String diaryDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalendarDietLog> calendarDietLogs = new ArrayList<>();

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalendarWorkoutLog> calendarWorkoutLogs = new ArrayList<>();
}