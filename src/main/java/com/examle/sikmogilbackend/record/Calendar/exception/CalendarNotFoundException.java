package com.examle.sikmogilbackend.record.Calendar.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class CalendarNotFoundException extends NotFoundGroupException {
    public CalendarNotFoundException(String message) {
        super(message);
    }

    public CalendarNotFoundException() {
        this("존재하지 않는 기록 입니다.");
    }
}
