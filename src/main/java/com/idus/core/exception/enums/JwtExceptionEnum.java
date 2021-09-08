package com.idus.core.exception.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Optional;
import lombok.Getter;

@Getter
@ApiModel(description = "JWT 관련 에러 정의")
public enum JwtExceptionEnum {

    NONE(
        100000,
        true,
        "NONE",
        "NONE"
    ),
    BAD_CREDENTIALS_EXCEPTION(
        100001,
        false,
        "BadCredentialsException -> the credentials are invalid",
        "잘못된 사용자 credential 정보 입니다."
    ),
    EXPIRED_JWT_EXCEPTION(

        100002,
        false,
        "ExpiredJwtException -> Exception indicating that a JWT was accepted after it expired and must be rejected.",
        "Jwt 유효기간이 초과 되었습니다."
    ),
    UNSUPPORTED_JWT_EXCEPTION(
        100003,
        false,
        "UnsupportedJwtException -> when receiving a JWT in a particular format/configuration that does not match the format expected",
        "Jwt 형식이 잘못 되었습니다."
    ),
    MALFORMED_JWT_EXCEPTION(
        100004,
        false,
        "MalformedJwtException -> Exception indicating that a JWT was not correctly constructed and should be rejected",
        "Jwt 구성 정보가 잘못 되었습니다."
    ),
    SECURITY_EXCEPTION(
        100005,
        false,
        "SecurityException -> Exception indicating that either calculating a signature or verifying an existing signature of a JWT failed.",
        "Jwt 기존 서명정보를 확인할 수 없습니다."
    ),
    ILLEGAL_ARGUMENT_EXCEPTION(
        100006,
        false,
        "IllegalArgumentException -> Thrown to indicate that a method has been passed an illegal or inappropriate argument",
        "메소드에 잘못된 인수가 전달 되었습니다."
    );

    @ApiModelProperty(value = "에러코드")
    private int code;

    @ApiModelProperty(value = "에러로그 파라미터 정보 존재 여부")
    private Boolean isMsgParam;

    @ApiModelProperty(value = "프로트/앱 반환용 에러 메시지")
    private String internalMsg;

    @ApiModelProperty(value = "키바나 에러로그 메시지")
    private String externalMsg;

    JwtExceptionEnum(int code, Boolean isMsgParam, String internalMsg, String externalMsg) {
        this.code = code;
        this.isMsgParam = Optional.ofNullable(isMsgParam).orElse(false);
        this.internalMsg = internalMsg;
        this.externalMsg = externalMsg;
    }

    
}
