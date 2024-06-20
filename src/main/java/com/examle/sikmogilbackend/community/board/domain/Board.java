package com.examle.sikmogilbackend.community.board.domain;

import com.examle.sikmogilbackend.member.domain.Member;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    @Schema(description = "게시글 id", example = "1")
    private Long boardId;

    @Schema(description = "카테고리", example = "다이어트, 운동, 자유")
    private Category category;

    @Schema(description = "게시글 제목", example = "제목")
    private String title;

    @Schema(description = "게시글 내용", example = "내용")
    @Column(columnDefinition="TEXT")
    private String content;

    @Schema(description = "게시글 날짜", example = "2024.06.21")
    private String boardDate;

    @Schema(description = "신고 횟수", example = "1")
    private int reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Schema(description = "작성자", example = "nickname")
    private Member writer;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    @Schema(description = "이미지")
    private List<BoardPicture> pictures = new ArrayList<>();

    @Builder
    private Board(Category category, String title, String content, String boardDate, Member writer) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.boardDate = boardDate;
        this.reportCount = 0;
        this.writer = writer;
    }

//    public void addPicture(String imageUrl) {
//        pictures.add(BoardPicture.builder()
//                .board(this)
//                .imageUrl(imageUrl)
//                .build());
//    }

}
