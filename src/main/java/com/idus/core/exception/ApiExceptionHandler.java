package com.idus.core.exception;

import com.idus.api.domain.common.ResponseCode;
import com.idus.api.domain.common.ResponseError;
import com.idus.api.domain.common.ResponseMeta;
import com.idus.api.domain.common.ResponseWrap;
import com.idus.core.exception.enums.JwtExceptionEnum;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiExceptionHandler {

    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<Object> jwtCustomExceptionHandler(JwtCustomException ce, HttpServletRequest req) {
        JwtExceptionEnum jwtEnum = ce.getJwtExceptionEnum();
        String formatter = "<{} : {}> {} , uri: {}?{}";
        log.error(formatter,
            jwtEnum.getCode(),
            jwtEnum.getExternalMsg(),
            jwtEnum.getInternalMsg(),
            Objects.isNull(req.getRequestURI()) ? "" : req.getRequestURI(),
            req.getQueryString()
        );
        ResponseMeta meta = ResponseMeta.builder().status(HttpStatus.UNPROCESSABLE_ENTITY.value()).code(jwtEnum.getCode()).message(
            ResponseCode.FAIL.getMessage()).build();
        ResponseError err = ResponseError.builder().method(req.getMethod()).path(req.getRequestURI()).message(JwtCustomException.class.toString()).detail(jwtEnum.getExternalMsg()).build();
        ResponseWrap wrap = new ResponseWrap(meta, err);
        return new ResponseEntity<>(wrap, getDefaultHttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> userExceptionHandler(UserException ue, HttpServletRequest req) {
        log.error("<{} : {}> message: {}, detail: {}",
            req.getMethod(),
            Objects.isNull(req.getRequestURI()) ? "" : req.getRequestURI(),
            ue.getMessage(),
            ue.getDetail()
        );
        ResponseMeta meta = ResponseMeta.builder().status(ue.getHttpStatus().value()).code(ue.getCode()).message(ResponseCode.FAIL.getMessage()).build();
        ResponseError err = ResponseError.builder().method(req.getMethod()).path(req.getRequestURI()).message(ue.getMessage()).detail(ue.getDetail()).build();
        ResponseWrap wrap = new ResponseWrap(meta, err);
        return new ResponseEntity<>(wrap, getDefaultHttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e, HttpServletRequest req) {
        log.error("ApiExceptionHandler stackTrace -> {}", ExceptionUtils.getStackTrace(e));
        ResponseWrap wrap = new ResponseWrap(
            ResponseMeta.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(999999).message("시스템 내부 에러 발생").build(),
            ResponseError.builder()
                .path(req.getRequestURI())
                .error(e.getClass().toString())
                .message("시스템 내부 에러 발생")
                .detail(ExceptionUtils.getStackTrace(e))
                .build()
        );
        return new ResponseEntity<>(wrap, getDefaultHttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }




    public HttpHeaders getDefaultHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON_UTF8, StandardCharsets.UTF_8);
        httpHeaders.setContentType(mediaType);
        return httpHeaders;
    }



}
