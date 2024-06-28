package com.examle.sikmogilbackend.community.comment.application;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.comment.api.dto.request.CommentSaveReqDto;
import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.community.comment.domain.repository.CommentRepository;
import com.examle.sikmogilbackend.global.util.GlobalUtil;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final GlobalUtil globalUtil;
    private final CommentRepository commentRepository;

    // 댓글 저장
    public Long commentSave(String email, CommentSaveReqDto commentSaveReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
        Board board = globalUtil.getBoardById(commentSaveReqDto.boardId());

        Comment comment = commentRepository.save(commentSaveReqDto.toEntity(member, board));

        return comment.getCommentId();
    }

    // 댓글 수정

    // 댓글 삭제

}
