package com.examle.sikmogilbackend.community.comment.exception;

import com.examle.sikmogilbackend.global.error.exception.AccessDeniedGroupException;

public class NotCommentOwnerException extends AccessDeniedGroupException {
    public NotCommentOwnerException(String message) {
        super(message);
    }

    public NotCommentOwnerException() {
        this("댓글의 소유자만 수정, 삭제할 수 있습니다.");
    }

}
