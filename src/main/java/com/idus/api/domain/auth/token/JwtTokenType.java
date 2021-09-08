package com.idus.api.domain.auth.token;

import lombok.Getter;

@Getter
public enum JwtTokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN;

    public static boolean isAccessToken(JwtTokenType jwtTokenType){
        return ACCESS_TOKEN == jwtTokenType;
    }
}
