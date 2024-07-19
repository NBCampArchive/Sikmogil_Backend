package com.examle.sikmogilbackend.community.comment.api.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record CommentListResDto(
        Page<CommentInfoResDto> commentInfoResDtos
) {
    public static CommentListResDto from(Page<CommentInfoResDto> commentInfoResDto) {
        return CommentListResDto.builder()
                .commentInfoResDtos(commentInfoResDto)
                .build();
    }
}
