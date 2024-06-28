package com.examle.sikmogilbackend.community.board.domain.repository;

import com.examle.sikmogilbackend.community.board.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
}
