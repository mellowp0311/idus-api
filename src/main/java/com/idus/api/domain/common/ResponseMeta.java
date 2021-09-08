package com.idus.api.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMeta {

    private int status;
    private int code;
    private String message;

    @Builder
    public ResponseMeta(Integer status, Integer code, String message){
        this.status = Optional.ofNullable(status).orElseGet(() -> 200);
        this.code = Optional.ofNullable(code).orElseGet(() -> 0);
        this.message = Optional.ofNullable(message).orElseGet(() -> "success");
    }


}
