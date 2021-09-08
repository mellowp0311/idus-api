package com.idus.api.domain.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@ApiModel(description = "토큰 정보")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Jwt {

    @JsonIgnore
    @ApiModelProperty(value = "사용자 시퀀스")
    private Long userSeq;

    @ApiModelProperty(value = "access token")
    private String accessToken;

    @ApiModelProperty(value = "refresh token")
    private String refreshToken;

    public Jwt(){}

    @Builder
    public Jwt(Long userSeq, String accessToken, String refreshToken) {
        this.userSeq = Optional.ofNullable(userSeq).orElse(0L);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
