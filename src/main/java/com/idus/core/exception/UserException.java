package com.idus.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
public class UserException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
    private final String detail;

    public UserException(int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.message = getClass().toString();
        this.detail = message;
    }

    public UserException(BindingResult bindingResult){
        super();
        FieldError err = bindingResult.getFieldErrors().get(0);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.code = 200000;
        this.message = getClass().toString();
        this.detail = String.format("An error occurred in the [%s] field. message -> %s" , err.getField(), err.getDefaultMessage());
    }


}
