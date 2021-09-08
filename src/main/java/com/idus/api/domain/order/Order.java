package com.idus.api.domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.idus.api.domain.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "주문정보")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @ApiModelProperty(value = "주문번호")
    private String orderNo;

    @ApiModelProperty(value = "상품정보")
    private Product product;

    @ApiModelProperty(value = "구매자 시퀀스")
    private Long buyerSeq;

    @ApiModelProperty(value = "결제일")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payDate;

}
