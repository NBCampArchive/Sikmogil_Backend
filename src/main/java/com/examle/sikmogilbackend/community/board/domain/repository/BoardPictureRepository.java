package com.examle.sikmogilbackend.community.board.domain.repository;

import com.examle.sikmogilbackend.community.board.domain.BoardPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPictureRepository extends JpaRepository<BoardPicture, Long> {
    void deleteByBoardBoardId(Long boardId);
}
