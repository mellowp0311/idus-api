package com.idus.core.exception;

import com.idus.core.exception.enums.JwtExceptionEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class JwtCustomException extends Exception {

    private static final long serialVersionUID = 1L;
    private List arguments = new ArrayList();
    
    private final JwtExceptionEnum jwtExceptionEnum;
    
    public JwtCustomException(JwtExceptionEnum jwtExceptionEnum) {
        super();
        this.jwtExceptionEnum = jwtExceptionEnum;
    }
    
    public JwtCustomException(JwtExceptionEnum jwtExceptionEnum, Object... message) {
        super();
        this.jwtExceptionEnum = jwtExceptionEnum;
        this.arguments.add(message);
    }

    
}
