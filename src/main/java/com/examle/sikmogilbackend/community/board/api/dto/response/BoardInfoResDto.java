package com.examle.sikmogilbackend.community.board.api.dto.response;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.BoardPicture;
import com.examle.sikmogilbackend.community.board.domain.Category;
import com.examle.sikmogilbackend.member.domain.Member;
import java.util.List;
import lombok.Builder;

@Builder
public record BoardInfoResDto(
        Long myMemberId,
        Long writerMemberId,
        Long boardId,
        Category category,
        String title,
        String content,
        List<String> imageUrl,
        int likeCount,
//        int commentCount,
        String nickname,
        String date
) {
    public static BoardInfoResDto of(Member member, Board board) {
        List<String> imageUrl = board.getPictures().stream()
                .map(BoardPicture::getImageUrl)
                .toList();

        return BoardInfoResDto.builder()
                .myMemberId(member.getMemberId())
                .writerMemberId(board.getWriter().getMemberId())
                .boardId(board.getBoardId())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .imageUrl(imageUrl)
                .likeCount(board.getLikeCount())
//                .commentCount(commentCount)
                .nickname(board.getWriter().getNickname())
                .date(board.getBoardDate())
                .build();
    }
}
