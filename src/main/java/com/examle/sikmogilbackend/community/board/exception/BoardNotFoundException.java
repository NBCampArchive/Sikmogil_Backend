package com.examle.sikmogilbackend.community.board.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class BoardNotFoundException extends NotFoundGroupException {

    public BoardNotFoundException(String message) {
        super(message);
    }

    public BoardNotFoundException() {
        this("존재하지 않는 게시글 입니다.");
    }
}
