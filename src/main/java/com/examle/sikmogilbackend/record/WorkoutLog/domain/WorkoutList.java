package com.examle.sikmogilbackend.record.WorkoutLog.domain;

import com.examle.sikmogilbackend.record.Calendar.domain.Calendar;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class WorkoutList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_list_id")
    @Schema(description = "캘린더와 기록들을 연결하기 위한 pk id", example = "1")
    private Long workoutListId;

    @Schema(description = "진행한 운동", example = "헬스")
    private String performedWorkout;

    @Schema(description = "운동한 시간", example = "50")
    protected Long workoutTime;

    @Schema(description = "운동한 세기", example = "100")
    protected Long workoutIntensity;

    @Schema(description = "운동 사진", example = "url")
    private String workoutPicture;

    @Schema(description = "운동한 칼로리", example = "100")
    protected Long calorieBurned;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "workout_log_id")
    private WorkoutLog workoutLog;

    public WorkoutListDTO toDTO() {
        return WorkoutListDTO.builder()
                .date(calendar.getDiaryDate())
                .workoutListId(workoutListId)
                .performedWorkout(performedWorkout)
                .workoutTime(workoutTime)
                .workoutIntensity(workoutIntensity)
                .workoutPicture(workoutPicture)
                .calorieBurned(calorieBurned)
                .build();
    }

    public void deleteWorkoutPicture() {
        this.workoutPicture = null;
    }
}
