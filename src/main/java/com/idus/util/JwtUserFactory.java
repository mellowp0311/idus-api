package com.idus.util;

import com.idus.api.domain.auth.token.JwtUser;
import com.idus.api.domain.user.User;

public class JwtUserFactory {

    private JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
            user.getUserSeq(),
            user.getUserId(),
            user.getUserPassword(),
            user.getUserStatus(),
            user.getUserType(),
            true
        );
    }

}
