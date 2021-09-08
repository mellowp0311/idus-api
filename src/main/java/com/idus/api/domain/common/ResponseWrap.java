package com.idus.api.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrap<T> {

    @ApiModelProperty(value = "meta 정보")
    private ResponseMeta meta;

    @ApiModelProperty(value = "응답 데이터")
    private T data;

    @ApiModelProperty(value = "error 정보")
    private ResponseError error;

    /**
     * Default constructor
     */
    public ResponseWrap() {
        this.meta = ResponseMeta.builder().build();
        this.data = (T) new ResponseEmpty();
    }

    /**
     * Response constructor
     *
     * @param data
     */
    public ResponseWrap(T data) {
        this.meta = ResponseMeta.builder().build();
        this.data = data;
    }

    /**
     * Error Response constructor
     *
     * @param meta
     * @param error
     */
    public ResponseWrap(ResponseMeta meta, ResponseError error){
        this.meta = meta;
        this.error = error;
    }


}
