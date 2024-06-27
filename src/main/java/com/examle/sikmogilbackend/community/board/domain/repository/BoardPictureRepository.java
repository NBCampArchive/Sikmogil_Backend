package com.examle.sikmogilbackend.community.board.domain.repository;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.BoardPicture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPictureRepository extends JpaRepository<BoardPicture, Long> {
    void deleteByBoardBoardId(Long boardId);

    void deleteByBoardAndImageUrlIn(Board board, List<String> urlsToDelete);
}
