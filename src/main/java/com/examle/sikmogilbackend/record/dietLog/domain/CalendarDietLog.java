package com.examle.sikmogilbackend.record.dietLog.domain;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutLog;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarDietLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_dietlog_id")
    @Schema(description = "캘린더와 기록들을 연결하기 위한 pk id", example = "1")
    private Long CalendarDietLogId;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Schema(description = "먹은 음식", example = "짜장면")
    private String foodName;

    @Schema(description = "칼로리", example = "100")
    protected Long calorie;

    @Schema(description = "음식 사진", example = "url")
    private String foodPicture;

    @Schema(description = "음식 먹은 시간대", example = "M: 아침, L: 점심, D:저녁")
    private String mealTime;

    @ManyToOne
    @JoinColumn(name = "diet_log_id")
    private DietLog dietLog;
}
