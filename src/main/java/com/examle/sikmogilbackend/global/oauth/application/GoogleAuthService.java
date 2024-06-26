package com.examle.sikmogilbackend.global.oauth.application;

import com.examle.sikmogilbackend.auth.api.dto.response.UserInfo;
import com.examle.sikmogilbackend.auth.application.AuthService;
import com.examle.sikmogilbackend.global.oauth.exception.OAuthException;
import com.examle.sikmogilbackend.member.domain.SocialType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoogleAuthService implements AuthService {

    private static final String JWT_DELIMITER = "\\.";

    private final ObjectMapper objectMapper;

    public GoogleAuthService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getProvider() {
        return String.valueOf(SocialType.GOOGLE).toLowerCase();
    }

    @Transactional
    @Override
    public UserInfo getUserInfo(String idToken) {
        String decodePayload = getDecodePayload(idToken);

        try {
            return objectMapper.readValue(decodePayload, UserInfo.class);
        } catch (JsonProcessingException e) {
            throw new OAuthException("id 토큰을 읽을 수 없습니다.");
        }
    }

    private String getDecodePayload(String idToken) {
        String payload = getPayload(idToken);

        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

    private String getPayload(String idToken) {
        return idToken.split(JWT_DELIMITER)[1];
    }

}
