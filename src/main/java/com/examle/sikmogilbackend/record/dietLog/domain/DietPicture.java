package com.examle.sikmogilbackend.record.dietLog.domain;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietListDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_picture_id")
    @Schema(description = "캘린더와 기록들을 연결하기 위한 pk id", example = "1")
    private Long dietPictureId;

    @Schema(description = "음식 사진", example = "url")
    private String foodPicture;

    @Schema(description = "먹은 날짜", example = "2024.06.02")
    protected String dietDate;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "diet_log_id")
    private DietLog dietLog;

    public DietPictureDTO toDTO(){
        return DietPictureDTO.builder()
                .dietPictureId(dietPictureId)
                .foodPicture(foodPicture)
                .dietDate(dietDate)
                .build();
    }
}
