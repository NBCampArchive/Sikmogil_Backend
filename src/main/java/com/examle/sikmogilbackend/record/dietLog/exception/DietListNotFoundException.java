package com.examle.sikmogilbackend.record.dietLog.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class DietListNotFoundException extends NotFoundGroupException {
    public DietListNotFoundException(String message) {
        super(message);
    }

    public DietListNotFoundException() {
        this("존재하지 않는 식단 리스트 입니다`.");
    }
}
