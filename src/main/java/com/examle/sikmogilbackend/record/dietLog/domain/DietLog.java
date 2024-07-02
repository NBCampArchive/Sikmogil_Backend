package com.examle.sikmogilbackend.record.dietLog.domain;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogDTO;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietLogInPictureDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @OneToMany(mappedBy = "dietLog", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DietList> dietLists = new ArrayList<>();

    @OneToMany(mappedBy = "dietLog", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DietPicture> dietPictures = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public DietLogDTO toDTO() {
        return DietLogDTO.builder()
                .waterIntake(waterIntake)
                .totalCalorieEaten(totalCalorieEaten)
                .dietDate(dietDate)
                .build();
    }

    public DietLogDTO toDTO(Long canEatCalorie) {
        return DietLogDTO.builder()
                .canEatCalorie(canEatCalorie)
                .waterIntake(waterIntake)
                .totalCalorieEaten(totalCalorieEaten)
                .dietDate(dietDate)
                .build();
    }

    public DietLogInPictureDTO toPictureDTO() {
        return DietLogInPictureDTO.builder()
                .dietPictureDTOS(
                        dietPictures.stream()
                                .map(DietPicture::toDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public void updateDietLog(DietLogDTO dietLogDTO) {
        this.dietDate = dietLogDTO.dietDate();
        this.waterIntake = dietLogDTO.waterIntake();
        this.totalCalorieEaten = dietLogDTO.totalCalorieEaten();
    }

}