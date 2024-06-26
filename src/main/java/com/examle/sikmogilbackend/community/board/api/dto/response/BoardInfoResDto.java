package com.examle.sikmogilbackend.community.board.api.dto.response;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.Category;
import java.util.List;
import lombok.Builder;

@Builder
public record BoardInfoResDto(
        Category category,
        String title,
        String content,
        List<String> imageUrl,
//        int likeCount,
//        int commentCount,
        String nickname,
        String date
) {
    public static BoardInfoResDto of(Board board, List<String> imageUrl) {
        return BoardInfoResDto.builder()
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .imageUrl(imageUrl)
//                .likeCount(likeCount)
//                .commentCount(commentCount)
                .nickname(board.getWriter().getNickname())
                .date(board.getBoardDate())
                .build();
    }
}
