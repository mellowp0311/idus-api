package com.idus.api.domain.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@ApiModel(description = "회원가입 요청 정보")
public class UserJoinRequest {

    @ApiModelProperty(value = "사용자 시퀀스", hidden = true)
    private Long userSeq;

    @ApiModelProperty(value = "사용자 이름", required = true)
    @NotBlank(message = "이름을 입력해주세요.")
    @Length(max = 20, message = "최대 사이즈는 20 입니다.")
    @Pattern(
        regexp = "^[a-zA-Z가-힣]*$",
        message = "한글,영문,대/소문자만 허용합니다."
    )
    private String userName;

    @ApiModelProperty(value = "사용자 닉네임", required = true)
    @NotBlank(message = "별명을 입력해주세요.")
    @Length(max = 30, message = "최대 사이즈는 30 입니다.")
    @Pattern(
        regexp = "^[a-z]*$",
        message = "영문,소문자만 허용합니다."
    )
    private String nickName;

    @ApiModelProperty(value = "사용자 패스워드", required = true)
    @NotBlank(message = "패스워드를 입력해주세요.")
    @Length(min = 10, max = 30, message = "패스워드는 10자리 이상 30자리 이하 입니다.")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{4,30}$",
        message = "영문 대문자, 영문 소문자, 특수문자, 숫자 각 1개 이상씩 포함해야 합니다"
    )
    private String userPassword;

    @ApiModelProperty(value = "사용자 연락처", required = true)
    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(
        regexp = "^[0-9]*$",
        message = "숫자만 입력해주세요."
    )
    @Length(min = 9, max = 20, message = "연락처는 9자리 이상 20자리 이하 입니다.")
    private String phoneNo;

    @ApiModelProperty(value = "사용자 ID (이메일)", required = true)
    @NotBlank(message = "이메일(사용자 ID)을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Length(max = 100, message = "이메일은 최대 100 자리 입니다.")
    private String userId;

    @ApiModelProperty(value = "회원 성별 (F:여성, M:남성, E:기타)")
    private String userGender;

}
