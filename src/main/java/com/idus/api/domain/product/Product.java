package com.idus.api.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "상품정보")
public class Product {

    @ApiModelProperty(value = "상품번호")
    private Long productSeq;

    @ApiModelProperty(value = "상품명")
    private String productTitle;

    @ApiModelProperty(value = "상품금액")
    private Integer productPrice;

}
