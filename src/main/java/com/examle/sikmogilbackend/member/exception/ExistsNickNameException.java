package com.examle.sikmogilbackend.member.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class ExistsNickNameException extends InvalidGroupException {
    public ExistsNickNameException(String message) {
        super(message);
    }

    public ExistsNickNameException() {
        this("이미 존재하는 닉네임입니다.");
    }
}
