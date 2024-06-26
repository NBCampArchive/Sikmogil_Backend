package com.examle.sikmogilbackend.community.board.domain.repository;

import static com.examle.sikmogilbackend.community.board.domain.QBoard.board;

import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.BoardPicture;
import com.examle.sikmogilbackend.community.board.domain.Category;
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
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardInfoResDto> findByCategoryWithBoard(String category, Pageable pageable) {
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
                .map(b -> BoardInfoResDto.of(b,
                        b.getPictures().stream()
                                .map(BoardPicture::getImageUrl)
                                .toList())
                )
                .toList();

        return new PageImpl<>(boardDtos, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardInfoResDto> findByBoardAll(Pageable pageable) {
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
                .map(b -> BoardInfoResDto.of(b,
                        b.getPictures().stream()
                                .map(BoardPicture::getImageUrl)
                                .toList())
                )
                .toList();

        return new PageImpl<>(boardDtos, pageable, total);
    }
}
