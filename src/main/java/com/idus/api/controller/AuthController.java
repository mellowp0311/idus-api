package com.idus.api.controller;


import com.idus.api.domain.auth.AuthenticationResponse;
import com.idus.api.domain.common.ResponseWrap;
import com.idus.api.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "사용자 토큰 API")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final JwtService jwtService;

    // TODO: 과제 시간 여유 있을 시, 갱신 로직 처리
    @PostMapping(value = "/re/authenticate")
    @ApiOperation(value = "토큰 재발급 요청", notes = "refresh token 으로 access token 재발급", response = AuthenticationResponse.class)
    public ResponseWrap<AuthenticationResponse> reAuthenticate() throws Exception{
        return new ResponseWrap(new AuthenticationResponse(jwtService.reAuthenticate()));
    }

}
