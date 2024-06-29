package com.examle.sikmogilbackend.community.comment.api.dto.response;

import com.examle.sikmogilbackend.community.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentInfoResDto(
        String writerProfileImage,
        Long writerMemberId,
        Long commentId,
        String content
) {
    public static CommentInfoResDto of(Comment comment) {
        return CommentInfoResDto.builder()
                .writerProfileImage(comment.getWriter().getPicture())
                .writerMemberId(comment.getWriter().getMemberId())
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .build();
    }
}
