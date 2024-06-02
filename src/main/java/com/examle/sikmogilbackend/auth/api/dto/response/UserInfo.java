package com.examle.sikmogilbackend.auth.api.dto.response;

public record UserInfo(
        String email,
        String name,
        String picture
) {
}
