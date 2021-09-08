package com.idus.api.domain.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSearch {

    @ApiModelProperty(value = "시작 인덱스")
    private Integer startIndex = 0;
    @ApiModelProperty(value = "검색 수량")
    private Integer searchQuantity = 10;
    @ApiModelProperty(value = "검색 타입(name: 사용자, email: 이메일)")
    private String searchType = "";
    @ApiModelProperty(value = "검색어")
    private String searchWord = "";
    @ApiModelProperty(hidden = true)
    private int page;
    public int getPage(){
        if(getStartIndex() < 0) this.startIndex = 0;
        if(getSearchQuantity() < 1) this.startIndex = 1;
        return startIndex * searchQuantity;
    }
}
