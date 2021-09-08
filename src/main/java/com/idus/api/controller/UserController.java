package com.idus.api.controller;

import com.idus.api.domain.auth.AuthenticationRequest;
import com.idus.api.domain.auth.AuthenticationResponse;
import com.idus.api.domain.common.ResponseWrap;
import com.idus.api.domain.user.User;
import com.idus.api.domain.user.UserJoinRequest;
import com.idus.api.domain.user.UserSearch;
import com.idus.api.service.JwtService;
import com.idus.api.service.UserService;
import com.idus.core.exception.JwtCustomException;
import com.idus.core.exception.UserException;
import com.idus.api.domain.auth.token.Jwt;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "사용자 처리 API")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/user")
    @ApiResponses({
        @ApiResponse(code = 200000, message = "파라미터 오류"),
        @ApiResponse(code = 200001, message = "아이디 또는 패스워드 오류"),
        @ApiResponse(code = 200002, message = "회원 중복 등록 오류")
    })
    @ApiOperation(value = "(1) 회원 가입", response = Boolean.class)
    public ResponseWrap<Boolean> saveUser(@Valid @RequestBody UserJoinRequest userJoinRequest, BindingResult result){
        if(result.hasErrors()) throw new UserException(result);
        return new ResponseWrap(userService.addUser(userJoinRequest));
    }


    @PostMapping(value = "/user/login")
    @ApiResponses({
        @ApiResponse(code = 100000, message = "파라미터 오류"),
        @ApiResponse(code = 100001, message = "회원 중복 등록 오류"),
    })
    @ApiOperation(value = "(2) 회원 로그인(인증)", notes = "로그인 성공 시 토큰 정보를 반환. 해당 토큰 정보를 Header에 담아 API를 호출 하도록 설정.", response = AuthenticationResponse.class)
    public ResponseWrap<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult result) throws JwtCustomException {
        if(result.hasErrors()) throw new UserException(result);
        Jwt token = jwtService.authenticate(authenticationRequest.getUserId(), authenticationRequest.getPassword());
        return new ResponseWrap(new AuthenticationResponse(token));
    }


    @PostMapping(value = "/user/logout")
    @ApiOperation(value = "(3) 회원 로그아웃", response = Boolean.class)
    public ResponseWrap<Boolean> logout(HttpServletRequest request) throws JwtCustomException {
        String jwtAuthentication = request.getHeader("Authorization");
        if(StringUtils.isNotEmpty(jwtAuthentication) && jwtAuthentication.contains("Bearer")){
            jwtAuthentication = jwtAuthentication.substring(7);
        }
        return new ResponseWrap(userService.logOut(jwtService.extractUserSeq(jwtAuthentication)));
    }


    @GetMapping("/user/{userSeq}")
    @ApiOperation(value = "(4) 단일 회원 상세 정보 조회", response = User.class)
    public ResponseWrap<User> findUserDetail(@PathVariable("userSeq") Long userSeq) {
        return new ResponseWrap(userService.searchUserInfo(userSeq));
    }


    @GetMapping("/user/order/{userSeq}")
    @ApiOperation(value = "(5) 단일 회원의 주문 목록 조회", response = User.class)
    public ResponseWrap<User> findUserOrderList(@PathVariable("userSeq") Long userSeq) {
        return new ResponseWrap(userService.searchUserOrderList(userSeq));
    }


    @GetMapping("/user/list")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "startIndex", value = "시작 인덱스, 0 이상, default 0"),
        @ApiImplicitParam(name = "searchQuantity", value = "검색 수량, 1 이상, default 10"),
        @ApiImplicitParam(name = "searchType", value = "검색 타입(name: 사용자, email: 이메일)"),
        @ApiImplicitParam(name = "searchWord", value = "검색어")
    })
    @ApiOperation(value = "(6) 여러 회원 목록 조회", notes = "요청한 파라미터 정보로 사용자 목록을 조회하여 반환 합니다.", response = List.class)
    public ResponseWrap<List<User>> findUserList(UserSearch userSearch) {
        return new ResponseWrap(userService.searchUserList(userSearch));
    }



}
