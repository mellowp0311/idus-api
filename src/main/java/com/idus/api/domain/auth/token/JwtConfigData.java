package com.idus.api.domain.auth.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "토큰 설정 정보")
public class JwtConfigData {

    @ApiModelProperty(value = "secret key")
    private final String key;
    @ApiModelProperty(value = "expire minute")
    private final long minute;

    public JwtConfigData(String key, Long minute) {
        this.key = key;
        this.minute = minute;
    }
}
