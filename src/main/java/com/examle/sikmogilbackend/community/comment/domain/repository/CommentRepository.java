package com.examle.sikmogilbackend.community.comment.domain.repository;

import com.examle.sikmogilbackend.community.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
