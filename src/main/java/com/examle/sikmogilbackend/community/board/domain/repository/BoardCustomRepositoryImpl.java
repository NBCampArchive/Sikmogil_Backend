package com.examle.sikmogilbackend.community.board.domain.repository;

import static com.examle.sikmogilbackend.community.board.domain.QBoard.board;
import static com.examle.sikmogilbackend.community.comment.domain.QComment.comment;

import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.Category;
import com.examle.sikmogilbackend.community.board.domain.QBoard;
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
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardInfoResDto> findByCategoryWithBoard(Member member, String category, Pageable pageable) {
        long total = queryFactory
                .selectFrom(board)
                .where(board.category.eq(Category.valueOf(category)))
                .fetchCount();

        List<Board> boards = queryFactory
                .selectFrom(board)
                .where(board.category.eq(Category.valueOf(category)))
                .orderBy(board.boardDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<BoardInfoResDto> boardDtos = boards.stream()
                .map(b -> BoardInfoResDto.of(member, b))
                .toList();

        return new PageImpl<>(boardDtos, pageable, total);
    }

    @Override
    public Page<BoardInfoResDto> findByBoardAll(Member member, Pageable pageable) {
        long total = queryFactory
                .selectFrom(board)
                .fetchCount();

        List<Board> boards = queryFactory
                .selectFrom(board)
                .orderBy(board.boardDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<BoardInfoResDto> boardDtos = boards.stream()
                .map(b -> BoardInfoResDto.of(member, b))
                .toList();

        return new PageImpl<>(boardDtos, pageable, total);
    }

    @Override
    public Board findByDetailBoard(Board board) {
        QBoard qBoard = QBoard.board;
        return queryFactory
                .selectFrom(qBoard)
                .leftJoin(qBoard.comments, comment).fetchJoin()
                .where(qBoard.boardId.eq(board.getBoardId()))
                .fetchOne();

    }
}
