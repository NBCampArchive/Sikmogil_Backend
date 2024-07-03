package com.examle.sikmogilbackend.challenge.exception;

import com.examle.sikmogilbackend.global.error.exception.NotFoundGroupException;

public class ChallengeNotFoundException extends NotFoundGroupException {

    public ChallengeNotFoundException(String message) {
        super(message);
    }

    public ChallengeNotFoundException() {
        this("존재하지 않는 챌린지 그룹 입니다.");
    }
}
