package com.examle.sikmogilbackend.community.board.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class ExistsBoardLikeException extends InvalidGroupException {
    public ExistsBoardLikeException(String message) {
        super(message);
    }
}
