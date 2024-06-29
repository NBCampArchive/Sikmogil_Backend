package com.examle.sikmogilbackend.community.comment.application;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.comment.api.dto.request.CommentSaveReqDto;
import com.examle.sikmogilbackend.community.comment.api.dto.request.CommentUpdateReqDto;
import com.examle.sikmogilbackend.community.comment.api.dto.response.CommentInfoResDto;
import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.community.comment.domain.repository.CommentRepository;
import com.examle.sikmogilbackend.community.comment.exception.NotCommentOwnerException;
import com.examle.sikmogilbackend.global.util.GlobalUtil;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final GlobalUtil globalUtil;
    private final CommentRepository commentRepository;

    // 댓글 저장
    public void commentSave(String email, CommentSaveReqDto commentSaveReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
        Board board = globalUtil.getBoardById(commentSaveReqDto.boardId());

        commentRepository.save(commentSaveReqDto.toEntity(member, board));
    }

    // 댓글 수정
    @Transactional
    public CommentInfoResDto commentUpdate(String email, Long commentId, CommentUpdateReqDto commentUpdateReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
        Comment comment = globalUtil.getCommentById(commentId);

        checkCommentOwnership(member, comment);

        comment.updateContent(commentUpdateReqDto.content());

        return CommentInfoResDto.of(comment);
    }

    // 댓글 삭제
    @Transactional
    public void commentDelete(String email, Long commentId) {
        Member member = globalUtil.getMemberByEmail(email);
        Comment comment = globalUtil.getCommentById(commentId);

        checkCommentOwnership(member, comment);

        commentRepository.delete(comment);
    }

    private static void checkCommentOwnership(Member member, Comment comment) {
        if (!member.getMemberId().equals(comment.getWriter().getMemberId())) {
            throw new NotCommentOwnerException();
        }
    }
}
