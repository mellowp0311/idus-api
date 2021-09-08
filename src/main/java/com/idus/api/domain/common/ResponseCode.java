package com.idus.api.domain.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"), FAIL(-1, "FAIL");

    private int code;

    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
