package com.examle.sikmogilbackend.auth.application;

import com.examle.sikmogilbackend.auth.api.dto.response.UserInfo;

public interface AuthService {
    UserInfo getUserInfo(String authCode);

    String getProvider();
}
