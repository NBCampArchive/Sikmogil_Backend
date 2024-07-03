package com.examle.sikmogilbackend.challenge.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class ChallengeLeaderException extends InvalidGroupException {
    public ChallengeLeaderException(String message) {
        super(message);
    }
}
