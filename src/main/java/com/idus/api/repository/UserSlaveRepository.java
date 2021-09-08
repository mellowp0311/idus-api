package com.idus.api.repository;

import com.idus.api.domain.user.User;
import com.idus.api.domain.user.UserSearch;
import com.idus.api.domain.order.Order;
import com.idus.core.annotation.Slave;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slave
@Repository
@Transactional(readOnly = true)
public interface UserSlaveRepository {

    User selectUserById(String userId);

    User selectUserByIdAndPassword(@Param("userId") String userId,
                                   @Param("userPassword") String userPassword);

    List<User> selectUserList(UserSearch userSearch);

    User selectUserBySeq(Long userSeq);

    List<Order> selectUserOrderList(Long userSeq);

}
