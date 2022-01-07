package com.pangjie.jpa.service;

import com.pangjie.doubleDBConfig.DataSource;
import com.pangjie.doubleDBConfig.DataSourceNames;
import com.pangjie.jpa.entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public interface UserInfoService {
    void save(UserInfo userInfo);

    UserInfo findById2(Integer userId);

    @DataSource(DataSourceNames.ONE)
    UserInfo findById(Integer userId);

    UserInfo findByOpenid(String openid);

    List<UserInfo> findAll();

    void updateLightLottery();

    Map<String, Object> queryAll(UserInfo userInfo, Pageable pageable);

    UserInfo findByUserName(String username);

    Map<String,Object> login(UserInfo userInfo);

    UserInfo register(UserInfo userInfo);
}
