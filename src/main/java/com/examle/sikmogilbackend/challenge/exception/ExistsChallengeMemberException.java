package com.examle.sikmogilbackend.challenge.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class ExistsChallengeMemberException extends InvalidGroupException {
    public ExistsChallengeMemberException(String message) {
        super(message);
    }
}
