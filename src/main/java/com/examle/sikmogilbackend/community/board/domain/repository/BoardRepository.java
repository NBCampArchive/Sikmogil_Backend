package com.examle.sikmogilbackend.community.board.domain.repository;

import com.examle.sikmogilbackend.community.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
}
