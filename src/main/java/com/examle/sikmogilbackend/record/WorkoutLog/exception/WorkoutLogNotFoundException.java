package com.examle.sikmogilbackend.record.WorkoutLog.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class WorkoutLogNotFoundException extends NotFoundGroupException {
    public WorkoutLogNotFoundException(String message) {
        super(message);
    }

    public WorkoutLogNotFoundException() {
        this("존재하지 않는 운동 기록 입니다`.");
    }
}
