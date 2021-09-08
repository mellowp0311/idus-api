package com.idus.api.service;

import com.idus.api.domain.user.User;
import com.idus.api.domain.user.UserJoinRequest;
import com.idus.api.domain.user.UserSearch;
import com.idus.api.repository.UserMasterRepository;
import com.idus.api.repository.UserSlaveRepository;
import com.idus.core.exception.UserException;
import com.idus.api.domain.auth.token.Jwt;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMasterRepository userMasterRepository;
    private final UserSlaveRepository userSlaveRepository;

    /**
     *
     * (1) 회원 가입
     * - 기본 유효성 검사는 @Valid 통해 처리.
     *
     * @param userJoinRequest 가입 정보
     * @return true: 성공, false: 실패
     */
    public Boolean addUser(UserJoinRequest userJoinRequest) {
        log.info("The Register request parameters. param: {}", userJoinRequest);
        // 아이디 중복 검사
        if(Objects.nonNull(userSlaveRepository.selectUserById(userJoinRequest.getUserId()))){
            throw new UserException(200001, "등록된 이메일(userID)이 존재합니다.");
        }
        boolean result = userMasterRepository.insertUser(userJoinRequest) > 0;
        log.info("The Register has been processed successfully. added userSeq: {}", userJoinRequest.getUserSeq());
        return result;
    }

    /**
     * (3) 회원 로그아웃
     *
     * @param userSeq 사용자 시퀀스
     * @return true: 성공, false: 실패
     */
    public Boolean logOut(Long userSeq) {
        userMasterRepository.removeUserJwtToken(userSeq);
        log.info("Logout has been successfully processed. userSeq: {}", userSeq);
        return true;
    }

    /**
     * (4) 단일 회원 상세 정보 조회
     *
     * @param userSeq 사용자 시퀀스
     * @return
     */
    @Transactional(readOnly = true)
    public User searchUserInfo(Long userSeq) {
        return userSlaveRepository.selectUserBySeq(userSeq);
    }


    /**
     * (5) 단일 회원의 주문 목록 조회
     *
     * @param userSeq 사용자 시퀀스
     * @return
     */
    public User searchUserOrderList(Long userSeq) {
        User user = this.searchUserInfo(userSeq);
        if( Objects.nonNull(user)){
            user.setOrderList(userSlaveRepository.selectUserOrderList(userSeq));
        }
        return user;
    }


    /**
     * (6) 여러 회원 목록 조회
     *
     * @param userSearch 검색조건
     * @return 검색조건에 적합한 회원 목록 반환
     */
    @Transactional(readOnly = true)
    public List<User> searchUserList(UserSearch userSearch){
        log.info("Inquire member information under the relevant conditions. param: {}", userSearch);
        return userSlaveRepository.selectUserList(userSearch);
    }

    /**
     * 사용자 JWT 갱신 처리
     *
     * @param jwt 토큰 정보
     */
    public void modifyJwtToken(Jwt jwt){
        userMasterRepository.updateUserJwtToken(jwt);
        log.info("The JWT token has been updated. jwt: {}", jwt);
    }



}
