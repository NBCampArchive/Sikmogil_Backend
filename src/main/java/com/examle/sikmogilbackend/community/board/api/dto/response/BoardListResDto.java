package com.examle.sikmogilbackend.community.board.api.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record BoardListResDto(
        Page<BoardInfoResDto> boardInfoResDtos
) {
    public static BoardListResDto from(Page<BoardInfoResDto> boardInfoResDto) {
        return BoardListResDto.builder()
                .boardInfoResDtos(boardInfoResDto)
                .build();
    }

}
