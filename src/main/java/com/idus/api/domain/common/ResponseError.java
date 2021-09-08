package com.idus.api.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseError {

    private LocalDateTime timestamp;
    private String method;
    private String path;
    private String error;
    private String message;
    private String detail;

    @Builder
    public ResponseError(String path, String method, String error, String message, String detail){
        this.timestamp = LocalDateTime.now();
        this.method = method;
        this.path = path;
        this.error = error;
        this.message = message;
        this.detail = detail;
    }


}
