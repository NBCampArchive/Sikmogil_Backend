package com.examle.sikmogilbackend.community.board.api;

import com.examle.sikmogilbackend.community.board.application.BoardLikeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/likes")
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    public BoardLikeController(BoardLikeService boardLikeService) {
        this.boardLikeService = boardLikeService;
    }

    // 게시글 좋아요

    // 게시글 좋아요 취소

}
