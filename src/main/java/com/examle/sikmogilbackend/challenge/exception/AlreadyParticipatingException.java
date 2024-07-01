package com.examle.sikmogilbackend.challenge.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class AlreadyParticipatingException extends InvalidGroupException {
    public AlreadyParticipatingException(String message) {
        super(message);
    }
}
