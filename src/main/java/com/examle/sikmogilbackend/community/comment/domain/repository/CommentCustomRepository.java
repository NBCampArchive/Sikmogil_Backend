package com.examle.sikmogilbackend.community.comment.domain.repository;

import com.examle.sikmogilbackend.community.comment.api.dto.response.CommentInfoResDto;
import com.examle.sikmogilbackend.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<CommentInfoResDto> findByBoardBoardId(Member member, Long boardId, Pageable pageable);
}
