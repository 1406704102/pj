package com.pangjie.jpa.repository;


import com.pangjie.jpa.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByOpenid(String openid);

    @Transactional
    @Modifying
    @Query("update UserInfo u set u.isLight = 0, u.isLottery = 0 ")
    void updateLightLottery();
}
