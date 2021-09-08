package com.idus.api.domain.auth.token;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
public class JwtUser implements UserDetails {

    private final Long userSeq;
    private final String userId;
    private final String password;
    private int status;
    private final int type;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long userSeq, String userId, String password, int status, int type, boolean enabled){
        this.userSeq = userSeq;
        this.userId = userId;
        this.password = password;
        this.status = status;
        this.type = type;
        this.enabled = enabled;
        this.authorities = new ArrayList<>();
    }

    @Builder
    public JwtUser(Long userSeq, String userId, int status, int type, boolean enabled){
        this.userSeq = userSeq;
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.enabled = enabled;
        this.authorities = new ArrayList<>();
        this.password = null;
    }


    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
