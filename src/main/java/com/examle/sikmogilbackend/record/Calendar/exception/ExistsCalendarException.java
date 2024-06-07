package com.examle.sikmogilbackend.record.Calendar.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class ExistsCalendarException extends InvalidGroupException {
    public ExistsCalendarException(String message) {
        super(message);
    }

    public ExistsCalendarException() {
        this("이미 해당 날짜의 컬럼이 존재합니다.");
    }
}
