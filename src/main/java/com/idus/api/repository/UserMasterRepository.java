package com.idus.api.repository;

import com.idus.api.domain.user.UserJoinRequest;
import com.idus.core.annotation.Master;
import com.idus.api.domain.auth.token.Jwt;
import org.springframework.stereotype.Repository;

@Master
@Repository
public interface UserMasterRepository {

    int insertUser(UserJoinRequest userJoinRequest);

    void updateUserJwtToken(Jwt jwt);

    void removeUserJwtToken(Long userSeq);
}
