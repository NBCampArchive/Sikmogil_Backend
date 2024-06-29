package com.examle.sikmogilbackend.community.comment.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class CommentNotFoundException extends NotFoundGroupException {
    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException() {
        this("존재하지 않는 댓글 입니다.");
    }
}
