package com.examle.sikmogilbackend.record.dietLog.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class DietPictureNotFoundException extends NotFoundGroupException {
    public DietPictureNotFoundException(String message) {
        super(message);
    }

    public DietPictureNotFoundException() {
        this("존재하지 않는 사진 입니다`.");
    }
}
