package com.idus.api.service;

import com.idus.api.domain.auth.token.Jwt;
import com.idus.api.domain.auth.token.JwtConfigData;
import com.idus.api.domain.auth.token.JwtTokenType;
import com.idus.core.exception.JwtCustomException;
import com.idus.core.exception.enums.JwtExceptionEnum;
import com.idus.properties.TokenProperties;
import com.idus.api.domain.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtService {

    private final TokenProperties tokenProperties;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final UserService userService;


    /**
     * 토큰 발행 및 인증 처리
     *
     * @param userId
     * @param password
     * @return
     * @throws JwtCustomException
     */
    /**
     * (2) 회원 로그인(인증)
     * - 아이디와 패스워드 확인 후, accessToken 을 발급.
     *
     * @param userId : 사용자 이메일(ID)
     * @param password : 패스워드
     * @return Jwt 토큰 정보
     * @throws JwtCustomException 토큰 검증 오류
     */
    public Jwt authenticate(String userId, String password) throws JwtCustomException {
        final Jwt jwt;
        try {
            final User user = customUserDetailService.findByUserIdAndUserPassword(userId, password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
            jwt = generateToken(user);
            // 보통 Token 정보는 Redis 를 통해 whiteList, blackList 를 관리 하지만, 임시로 RDB 사용
            // 과제 logout 기능 처리를 위해 임시로 처리.
            userService.modifyJwtToken(jwt);
            log.info("authenticate success. token: {}", jwt);
        } catch (BadCredentialsException e) {
            throw new JwtCustomException(JwtExceptionEnum.BAD_CREDENTIALS_EXCEPTION);
        }
        return jwt;
    }


    /**
     * 토큰 재발행
     *
     * @return
     * @throws JwtCustomException
     */
    public Jwt reAuthenticate() throws JwtCustomException {
        final Jwt jwt;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final User user = customUserDetailService.findByUserId(authentication.getName());
            jwt = generateToken(user);
            log.info("reAuthenticate success. token: {}", jwt);
        } catch (BadCredentialsException e) {
            throw new JwtCustomException(JwtExceptionEnum.BAD_CREDENTIALS_EXCEPTION);
        }
        return jwt;
    }


    /**
     * 토큰 발급 처리.
     *
     * @param user: 사용자 정보
     * @return Token
     */
    private Jwt generateToken(User user) {
        return Jwt.builder()
            .userSeq(user.getUserSeq())
            .accessToken( createToken(user, JwtTokenType.ACCESS_TOKEN))
            .refreshToken(createToken(user, JwtTokenType.REFRESH_TOKEN))
            .build();
    }


    /**
     * 토큰 생성
     *
     * @param user
     * @param jwtTokenType
     * @return
     */
    private String createToken(User user, JwtTokenType jwtTokenType) {
        JwtConfigData jcd = makeTokenTypeData(jwtTokenType);
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime expTime = now.plusMinutes(jcd.getMinute());
        return Jwts.builder()
            .setIssuer("idus-api")
            .setHeaderParam("typ", Header.JWT_TYPE)
            .setClaims(user.toClaims())
            .setSubject(user.getUserId())
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(expTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(Keys.hmacShaKeyFor(jcd.getKey().getBytes(StandardCharsets.UTF_8)))
            .compact();
    }


    /**
     * 토큰 타입에 따른 키값 설정
     *
     * @param jwtTokenType
     * @return
     */
    private JwtConfigData makeTokenTypeData(JwtTokenType jwtTokenType) {
        return JwtTokenType.isAccessToken(jwtTokenType) ?
            new JwtConfigData(tokenProperties.getAccessTokenSecretKey(), tokenProperties.getAccessTokenExpireMinute()):
            new JwtConfigData(tokenProperties.getRefreshTokenSecretKey(), tokenProperties.getRefreshTokenExpireMinute());
    }


    /**
     * 토큰 만료 여부
     *
     * @param token
     * @return
     * @throws JwtCustomException
     */
    private Boolean isTokenExpired(String token, JwtTokenType jwtTokenType) throws JwtCustomException {
        Date date = this.extractExpiration(token, jwtTokenType);
        return date.before(new Date());
    }


    /**
     * 토큰 만료시간 추출
     *
     * @param token
     * @param jwtTokenType
     * @return
     * @throws JwtCustomException
     */
    private Date extractExpiration(String token, JwtTokenType jwtTokenType) throws JwtCustomException {
        return this.extractClaim(token, Claims::getExpiration, jwtTokenType);
    }


    /**
     * Claim 추출
     *
     * @param <T>
     * @param token
     * @param claimsResolver
     * @param jwtTokenType
     * @return
     * @throws JwtCustomException
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, JwtTokenType jwtTokenType) throws JwtCustomException {
        JwtConfigData ttd = this.makeTokenTypeData(jwtTokenType);
        final Claims claims = extractAllClaims(token , ttd);;
        return claimsResolver.apply(claims);
    }

    public Long extractUserSeq(String token) throws JwtCustomException {
        JwtConfigData jwtConfigData = this.makeTokenTypeData(JwtTokenType.ACCESS_TOKEN);
        Claims claims = extractAllClaims(token, jwtConfigData);
        return ((Number)(Optional.ofNullable(claims.get("userSeq")).orElse(0))).longValue();
    }


    /**
     * 토큰 파싱
     *
     * @param token
     * @return
     * @throws JwtCustomException
     */
    private Claims extractAllClaims(String token, JwtConfigData jcd) throws JwtCustomException {
        Claims body = null;
        try {
            body = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jcd.getKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtCustomException(JwtExceptionEnum.EXPIRED_JWT_EXCEPTION);
        } catch (UnsupportedJwtException e) {
            throw new JwtCustomException(JwtExceptionEnum.UNSUPPORTED_JWT_EXCEPTION);
        } catch (MalformedJwtException e) {
            throw new JwtCustomException(JwtExceptionEnum.MALFORMED_JWT_EXCEPTION);
        } catch (SecurityException e) {
            throw new JwtCustomException(JwtExceptionEnum.SECURITY_EXCEPTION);
        } catch (IllegalArgumentException e) {
            throw new JwtCustomException(JwtExceptionEnum.ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (Exception e){
            e.printStackTrace();;
        }
        return body;
    }


    /**
     * 토큰에서 사용자 이름 추출
     *
     * @param token
     * @return
     * @throws JwtCustomException
     */
    public String extractUsername(String token, JwtTokenType jwtTokenType) throws JwtCustomException {
        return this.extractClaim(token, Claims::getSubject, jwtTokenType);
    }


    /**
     * 토큰 검증
     *
     * @param token
     * @param userDetails
     * @return
     * @throws JwtCustomException
     */
    public Boolean validateToken(String token, UserDetails userDetails, JwtTokenType jwtTokenType) throws JwtCustomException {
        final String username = this.extractUsername(token, jwtTokenType);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, jwtTokenType));
    }


}
