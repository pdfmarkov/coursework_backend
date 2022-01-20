package com.krivonosovmarkov.persona.coursework_backend.module.responses;

import lombok.Data;

@Data
public class TokenRefreshResponseDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
