package com.examle.sikmogilbackend.record.dietLog.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class DietLogNotFoundException extends NotFoundGroupException {
    public DietLogNotFoundException(String message) {
        super(message);
    }

    public DietLogNotFoundException() {
        this("존재하지 않는 식단 입니다`.");
    }
}
