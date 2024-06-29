package com.examle.sikmogilbackend.community.comment.api.dto.request;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.member.domain.Member;

public record CommentSaveReqDto(
        Long boardId,
        String content
) {
    public Comment toEntity(Member member, Board board) {
        return Comment.builder()
                .content(content)
                .writer(member)
                .board(board)
                .build();
    }
}
