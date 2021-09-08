package com.idus.api.service;

import com.idus.api.domain.user.User;
import com.idus.api.repository.UserSlaveRepository;
import com.idus.core.exception.UserException;
import com.idus.util.JwtUserFactory;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailService implements UserDetailsService {

    private final UserSlaveRepository userSlaveRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return JwtUserFactory.create(findByUserId(username));
    }

    public User findByUserId(String userId){
        final User user = userSlaveRepository.selectUserById(userId);
        this.validate(user);
        return user;
    }

    public User findByUserIdAndUserPassword(String userId, String password){
        User user = userSlaveRepository.selectUserByIdAndPassword(userId, password);
        this.validate(user);
        return user;
    }

    private void validate(User user){
        if(Objects.isNull(user) || StringUtils.isEmpty(user.getUserPassword())){
            throw new UserException(200001, "일치하는 정보가 없습니다.");
        }
    }


}
