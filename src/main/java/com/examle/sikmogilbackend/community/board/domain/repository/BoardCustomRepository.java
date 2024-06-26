package com.examle.sikmogilbackend.community.board.domain.repository;

import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page<BoardInfoResDto> findByCategoryWithBoard(String category, Pageable pageable);

    Page<BoardInfoResDto> findByBoardAll(Pageable pageable);
}
