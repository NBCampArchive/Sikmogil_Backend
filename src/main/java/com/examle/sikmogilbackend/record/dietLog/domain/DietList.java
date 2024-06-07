package com.examle.sikmogilbackend.record.dietLog.domain;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietListDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_list_id")
    @Schema(description = "캘린더와 기록들을 연결하기 위한 pk id", example = "1")
    private Long dietListId;

    @Schema(description = "먹은 음식", example = "짜장면")
    private String foodName;

    @Schema(description = "칼로리", example = "100")
    protected Long calorie;

    @Schema(description = "음식 사진", example = "url")
    private String foodPicture;

    @Schema(description = "음식 먹은 시간대", example = "M: 아침, L: 점심, D:저녁")
    private String mealTime;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "diet_log_id")
    private DietLog dietLog;

    public DietListDTO toDTO(){
        return DietListDTO.builder()
                .dietListId(dietListId)
                .calorie(calorie)
                .foodName(foodName)
                .foodPicture(foodPicture)
                .mealTime(mealTime)
                .build();
    }
}
