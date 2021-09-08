package com.idus.api.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import com.idus.api.domain.order.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "사용자 정보")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @ApiModelProperty(value = "사용자 시퀀스")
    private Long userSeq;

    @ApiModelProperty(value = "사용자 ID (이메일)")
    private String userId;

    @ApiModelProperty(value = "사용자 패스워드")
    private String userPassword;

    @ApiModelProperty(value = "사용자 이름")
    private String userName;

    @ApiModelProperty(value = "사용자 닉네임")
    private String nickName;

    @ApiModelProperty(value = "사용자 연락처")
    private String phoneNo;

    @ApiModelProperty(value = "사용자 타입 (0: 일반, 1:관리자, 2:기타)")
    private int userType;

    @ApiModelProperty(value = "회원 성별 (F:여성, M:남성, E:기타)")
    private String userGender;

    @ApiModelProperty(value = "사용자 상태 (0:정상 1:회원탈퇴 2:블랙 3:정지 4:관리자삭제,5:기타)")
    private int userStatus;

    @ApiModelProperty(value = "등록 일시")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "수정 일시")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updDate;

    @ApiModelProperty(value = "최근 주문 정보")
    private Order lastOrder;

    @ApiModelProperty(value = "주문 목록")
    private List<Order> orderList;

    public Map<String,Object> toClaims(){
        return ImmutableMap.<String,Object>builder()
            .put("userSeq"    , getUserSeq())
            .put("userId"     , getUserId())
            .put("userType"   , getUserType())
            .put("userStatus" , getUserStatus())
            .build();
    }
}
