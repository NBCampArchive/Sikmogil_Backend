package com.examle.sikmogilbackend.community.board.application;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.BoardLike;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardLikeRepository;
import com.examle.sikmogilbackend.global.util.GlobalUtil;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardLikeService {
    private final GlobalUtil globalUtil;
    private final BoardLikeRepository boardLikeRepository;

    // 게시글 좋아요
    @Transactional
    public void addBoardLike(String email, Long boardId) {
        Member member = globalUtil.getMemberByEmail(email);
        Board board = globalUtil.getBoardById(boardId);

        // 중복 좋아요 검사

        board.updateLikeCount();
        boardLikeRepository.save(BoardLike.builder()
                .board(board)
                .member(member)
                .build());
    }

    // 게시글 좋아요 취소

}
