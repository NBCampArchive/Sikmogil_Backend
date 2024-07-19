package com.examle.sikmogilbackend.community.comment.api.dto.response;

import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.Builder;

@Builder
public record CommentInfoResDto(
        Long myMemberId,
        String writerProfileImage,
        Long writerMemberId,
        Long commentId,
        String content
) {
    public static CommentInfoResDto of(Member member, Comment comment) {
        return CommentInfoResDto.builder()
                .myMemberId(member.getMemberId())
                .writerProfileImage(comment.getWriter().getPicture())
                .writerMemberId(comment.getWriter().getMemberId())
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .build();
    }
}
