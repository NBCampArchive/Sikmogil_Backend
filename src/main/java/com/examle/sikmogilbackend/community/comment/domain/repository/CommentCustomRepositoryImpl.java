package com.examle.sikmogilbackend.community.comment.domain.repository;

import static com.examle.sikmogilbackend.community.comment.domain.QComment.comment;

import com.examle.sikmogilbackend.community.comment.api.dto.response.CommentInfoResDto;
import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentInfoResDto> findByBoardBoardId(Member member, Long boardId, Pageable pageable) {
        long total = queryFactory
                .selectFrom(comment)
                .stream()
                .count();

        List<Comment> comments = queryFactory
                .selectFrom(comment)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<CommentInfoResDto> commentDtos = comments.stream()
                .map(c -> CommentInfoResDto.of(member, c))
                .toList();

        return new PageImpl<>(commentDtos, pageable, total);
    }
}
