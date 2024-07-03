package com.examle.sikmogilbackend.record.Calendar.domain;

import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.record.Calendar.api.dto.CalendarDTO;
import com.examle.sikmogilbackend.record.Calendar.api.dto.WeekWeightDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.api.dto.WorkoutListDTO;
import com.examle.sikmogilbackend.record.WorkoutLog.domain.WorkoutList;
import com.examle.sikmogilbackend.record.dietLog.api.dto.DietPictureDTO;
import com.examle.sikmogilbackend.record.dietLog.domain.DietPicture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    @Schema(description = "캘린더 id", example = "1")
    private Long calendarId;

    @Schema(description = "한 줄 일기", example = "오늘 하루 좋았다.")
    @Column(columnDefinition = "TEXT")
    private String diaryText;

    @Schema(description = "해당 날짜의 체중", example = "오늘 하루 좋았다.")
    private Double diaryWeight;

    @Schema(description = "일기 날짜", example = "2024.06.02")
    protected String diaryDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<DietPicture> dietPictures = new ArrayList<>();

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<WorkoutList> workoutLists = new ArrayList<>();


    public CalendarDTO toDTO() {
        return CalendarDTO.builder()
                .diaryDate(diaryDate)
                .diaryText(diaryText)
                .diaryWeight(diaryWeight)
                .workoutLists(workoutLists.stream()
                        .map(WorkoutList::toDTO)
                        .collect(Collectors.toList())
                )
                .dietPictures(dietPictures.stream()
                        .map(DietPicture::toDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public CalendarDTO toDTO(List<DietPictureDTO> dietPictureDTOS, List<WorkoutListDTO> workoutListDTOS) {
        return CalendarDTO.builder()
                .diaryDate(diaryDate)
                .diaryText(diaryText)
                .diaryWeight(diaryWeight)
                .dietPictures(dietPictureDTOS)
                .workoutLists(workoutListDTOS)
                .build();
    }

    public WeekWeightDTO toWeekDTO() {
        return WeekWeightDTO.builder()
                .date(diaryDate)
                .weight(diaryWeight)
                .build();
    }

    public void updateCalendar(CalendarDTO calendarDTO) {
        this.diaryDate = calendarDTO.diaryDate();
        this.diaryText = calendarDTO.diaryText();
    }

    public void updateWeight(Double weight) {
        this.diaryWeight = weight;
    }


}