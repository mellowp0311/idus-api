package com.idus.filter;

import com.idus.api.service.CustomUserDetailService;
import com.idus.api.service.JwtService;
import com.idus.api.domain.auth.token.JwtTokenType;
import com.idus.properties.TokenProperties;
import io.micrometer.core.instrument.util.StringUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;
    private final TokenProperties tokenProperties;
    private final Pattern bearer;

    public JwtRequestFilter(CustomUserDetailService customUserDetailService,
                            TokenProperties tokenProperties,
                            @Lazy JwtService jwtService){
        this.customUserDetailService = customUserDetailService;
        this.tokenProperties = tokenProperties;
        this.jwtService = jwtService;
        this.bearer = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            this.running(request);
        } catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }


    private void running(HttpServletRequest request){
        if(Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())){
            return;
        }
        final String uri = request.getRequestURI();
        final String authHeaderToken = obtainAuthorizationToken(request);
        if(Objects.nonNull(authHeaderToken) && StringUtils.isNotBlank(authHeaderToken)){
            try {
                JwtTokenType jwtTokenType = getTokenType(uri);
                String userId = jwtService.extractUsername(authHeaderToken, jwtTokenType);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(userId);
                // 토큰의 유효성을 확인
                if(jwtService.validateToken(authHeaderToken, userDetails, jwtTokenType)) {
                    UsernamePasswordAuthenticationToken upat;
                    upat = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails( request));
                    // Security 에 인증값 추가
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Jwt Token 타입 반환
     *
     * @param uri
     * @return
     */
    private JwtTokenType getTokenType(String uri){
        JwtTokenType tokenType = JwtTokenType.ACCESS_TOKEN;
        if(tokenProperties.getAccessTokenRefreshUrl().equalsIgnoreCase(uri)){
            tokenType = JwtTokenType.REFRESH_TOKEN;
        }
        return tokenType;
    }

    /**
     * Header Authorization 추출 (token)
     *
     * @param request
     * @return
     */
    private String obtainAuthorizationToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            try {
                token = URLDecoder.decode(token, "UTF-8");
                String[] parts = token.split(" ");
                if (parts.length == 2) {
                    String scheme = parts[0];
                    String credentials = parts[1];
                    return bearer.matcher(scheme).matches() ? credentials : null;
                }
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return token;
    }


}
