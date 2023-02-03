package com.onlyfresh.devkurly.web.dto.jwt;

import lombok.Data;

@Data
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    public TokenInfo(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }

    public TokenInfo(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public String getHeader() {
        return this.grantType + " " + accessToken;
    }
}
