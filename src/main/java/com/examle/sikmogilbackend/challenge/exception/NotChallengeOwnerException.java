package com.examle.sikmogilbackend.challenge.exception;

import com.examle.sikmogilbackend.global.error.exception.AccessDeniedGroupException;

public class NotChallengeOwnerException extends AccessDeniedGroupException {
    public NotChallengeOwnerException(String message) {
        super(message);
    }

    public NotChallengeOwnerException() {
        this("챌린지 그룹의 소유자만 수정, 삭제할 수 있습니다.");
    }
}
