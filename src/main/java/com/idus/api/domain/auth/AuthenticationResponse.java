package com.idus.api.domain.auth;

import com.idus.api.domain.auth.token.Jwt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "토큰 발급 응답 정보")
public class AuthenticationResponse {

    @ApiModelProperty(value = "JWT 토큰 정보")
    private Jwt jwt;

    public AuthenticationResponse(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse [jwt=" + jwt + "]";
    }
    
    
}
