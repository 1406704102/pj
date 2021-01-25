package com.pangjie.jpa.service;

import com.pangjie.doubleDBConfig.DataSource;
import com.pangjie.doubleDBConfig.DataSourceNames;
import com.pangjie.jpa.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface UserInfoService {
    void save(UserInfo userInfo);

    UserInfo findById2(Integer userId);

    @DataSource(DataSourceNames.ONE)
    UserInfo findById(Integer userId);

    UserInfo findByOpenid(String openid);

    List<UserInfo> findAll();

    void updateLightLottery();

    Page<UserInfo> queryAll(UserInfo userInfo, Pageable pageable);
}
