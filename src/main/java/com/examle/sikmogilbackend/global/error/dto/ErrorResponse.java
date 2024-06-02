package com.examle.sikmogilbackend.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}