package com.examle.sikmogilbackend.record.WorkoutLog.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class WorkoutListNotEqualsException extends InvalidGroupException {
    public WorkoutListNotEqualsException(String message) {
        super(message);
    }

    public WorkoutListNotEqualsException() {
        this("해당 사용자와 같지 않습니다.");
    }
}
