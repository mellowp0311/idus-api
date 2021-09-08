package com.idus.api.domain.auth;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "토큰 발급 요청 정보")
public class AuthenticationRequest {

    @NotBlank(message = "이메일(사용자ID)을 입력해주세요.")
    private String userId;
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
    
    public AuthenticationRequest() {}
    
    public AuthenticationRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest [userId=" + userId + ", password=" + password + "]";
    }
    
 }
