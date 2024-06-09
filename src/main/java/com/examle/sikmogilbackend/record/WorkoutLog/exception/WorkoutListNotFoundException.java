package com.examle.sikmogilbackend.record.WorkoutLog.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class WorkoutListNotFoundException extends NotFoundGroupException {
    public WorkoutListNotFoundException(String message) {
        super(message);
    }

    public WorkoutListNotFoundException() {
        this("존재하지 않는 운동 리스트입니다`.");
    }
}
