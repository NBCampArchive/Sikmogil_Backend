package com.examle.sikmogilbackend.community.board.exception;

import com.examle.sikmogilbackend.global.error.exception.InvalidGroupException;

public class ExistsReportException extends InvalidGroupException {
    public ExistsReportException(String message) {
        super(message);
    }

    public ExistsReportException() {
        this("중복 신고는 불가능합니다.");
    }
}
