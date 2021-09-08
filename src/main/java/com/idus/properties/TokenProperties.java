package com.idus.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class TokenProperties {

    @ApiModelProperty(value = "access 토큰 갱신 URL")
    private String accessTokenRefreshUrl;
    @ApiModelProperty(value = "access 토큰 secret key")
    private String accessTokenSecretKey;
    @ApiModelProperty(value = "access 토큰 만료 시간(분단위)")
    private long accessTokenExpireMinute;
    @ApiModelProperty(value = "refresh 토큰 secret key")
    private String refreshTokenSecretKey;
    @ApiModelProperty(value = "refresh 토큰 만료 시간(분단위)")
    private long refreshTokenExpireMinute;
}
