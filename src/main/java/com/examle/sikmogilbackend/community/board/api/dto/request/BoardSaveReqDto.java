package com.examle.sikmogilbackend.community.board.api.dto.request;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.Category;
import com.examle.sikmogilbackend.member.domain.Member;
import java.util.List;

public record BoardSaveReqDto(
        String title,
        Category category,
        String content,
        List<String> imageUrl
) {
    public Board toEntity(Member member) {
        return Board.builder()
                .title(title)
                .category(category)
                .content(content)
                .writer(member)
                .build();
    }
}
