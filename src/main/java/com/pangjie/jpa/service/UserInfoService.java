package com.pangjie.jpa.service;

import com.pangjie.dynamicDBConfig.DataSourceEnum;
import com.pangjie.dynamicDBConfig.TargetDataSource;
import com.pangjie.jpa.entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public interface UserInfoService {
    UserInfo save(UserInfo userInfo);

    UserInfo save2(UserInfo userInfo);

    List<UserInfo> saveUsers(UserInfo userInfo);

    UserInfo findById2(Integer userId);

    @TargetDataSource(DataSourceEnum.master)
    UserInfo findById(Integer userId);

    UserInfo findByOpenid(String openid);

    List<UserInfo> findAll();

    void updateLightLottery();

    Map<String, Object> queryAll(UserInfo userInfo, Pageable pageable);

    UserInfo findByUserName(String username);

    UserInfo findByUserName2(String username);

    Map<String,Object> login(UserInfo userInfo);

    UserInfo register(UserInfo userInfo);

    List<UserInfo> findByUserNameOfInside(String userName);
}
