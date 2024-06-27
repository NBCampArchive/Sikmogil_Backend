package com.examle.sikmogilbackend.gcs.api.dto;

import java.util.List;

public record DeleteBoardImageReqDto(
        Long boardId,
        List<String> imageUrl
) {
}
