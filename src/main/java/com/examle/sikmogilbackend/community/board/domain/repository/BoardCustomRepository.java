package com.examle.sikmogilbackend.community.board.domain.repository;

import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page<BoardInfoResDto> findByCategoryWithBoard(Member member, String category, Pageable pageable);

    Page<BoardInfoResDto> findByBoardAll(Member member, Pageable pageable);

    Board findByDetailBoard(Board board);
}
