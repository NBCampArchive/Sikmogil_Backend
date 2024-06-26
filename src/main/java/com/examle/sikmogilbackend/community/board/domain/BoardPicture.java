package com.examle.sikmogilbackend.community.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_picture_id")
    @Schema(description = "게시글의 사진 id", example = "1")
    private Long boardPictureId;

    @Schema(description = "게시글 이미지 URL", example = "https://~~")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private BoardPicture(String imageUrl, Board board) {
        this.imageUrl = imageUrl;
        this.board = board;
    }

}
