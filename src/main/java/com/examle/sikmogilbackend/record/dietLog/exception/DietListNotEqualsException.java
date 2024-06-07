package com.examle.sikmogilbackend.record.dietLog.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class DietListNotEqualsException extends InvalidGroupException {
    public DietListNotEqualsException(String message) {
        super(message);
    }

    public DietListNotEqualsException() {
        this("해당 사용자와 같지 않습니다.");
    }
}
