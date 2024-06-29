package com.examle.sikmogilbackend.community.board.domain;

import com.examle.sikmogilbackend.community.board.api.dto.request.BoardUpdateReqDto;
import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Enumerated(value = EnumType.STRING)
    @Schema(description = "카테고리", example = "다이어트, 운동, 자유")
    private Category category;

    @Schema(description = "게시글 제목", example = "제목")
    private String title;

    @Schema(description = "게시글 내용", example = "내용")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Schema(description = "게시글 날짜", example = "2024.06.21")
    private String boardDate;

    @Schema(description = "신고 횟수", example = "1")
    private int reportCount;

    @Schema(description = "좋아요 개수", example = "1")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Schema(description = "작성자", example = "nickname")
    private Member writer;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    @Schema(description = "이미지")
    private List<BoardPicture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Board(Category category, String title, String content, Member writer) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.boardDate = String.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        this.reportCount = 0;
        this.likeCount = 0;
        this.writer = writer;
    }

    public void boardUpdate(BoardUpdateReqDto boardUpdateReqDto) {
        this.title = boardUpdateReqDto.title();
        this.category = boardUpdateReqDto.category();
        this.content = boardUpdateReqDto.content();
    }

    public void updateLikeCount() {
        this.likeCount++;
    }

    public void cancelLikeCount() {
        if (this.likeCount <= 0) {
            this.likeCount = 0;
        } else {
            this.likeCount--;
        }
    }

}
