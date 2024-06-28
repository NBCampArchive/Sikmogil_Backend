package com.examle.sikmogilbackend.community.comment.api.dto.response;

import com.examle.sikmogilbackend.community.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentInfoResDto(
        Long writerMemberId,
        Long commentId,
        String content
) {
    public static CommentInfoResDto of(Comment comment) {
        return CommentInfoResDto.builder()
                .writerMemberId(comment.getWriter().getMemberId())
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .build();
    }
}
