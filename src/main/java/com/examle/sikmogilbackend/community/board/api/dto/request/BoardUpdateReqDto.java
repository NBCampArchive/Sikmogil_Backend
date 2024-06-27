package com.examle.sikmogilbackend.community.board.api.dto.request;

import com.examle.sikmogilbackend.community.board.domain.Category;
import java.util.List;

public record BoardUpdateReqDto(
        String title,
        Category category,
        String content,
        List<String> newImageUrl
) {
}
