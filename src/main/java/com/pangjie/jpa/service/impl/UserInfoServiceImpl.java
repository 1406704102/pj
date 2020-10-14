package com.pangjie.jpa.service.impl;


import com.pangjie.jpa.config.QueryHelp;
import org.springframework.data.domain.Page;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.repository.UserInfoRepo;
import com.pangjie.jpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepo userInfoRepo;
    @Override
    public void save(UserInfo userInfo) {
        userInfoRepo.save(userInfo);
    }

    @Override
    public UserInfo findById(Integer userId) {
        Optional<UserInfo> byId = userInfoRepo.findById(userId);
        return byId.get();
    }

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepo.findByOpenid(openid);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepo.findAll();
    }

    @Override
    public void updateLightLottery() {
        userInfoRepo.updateLightLottery();
    }

    @Override
    public Page<UserInfo> queryAll(UserInfo userInfo, Pageable pageable) {
        Page<UserInfo> all = userInfoRepo.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, userInfo, criteriaBuilder), pageable);
        return all;
    }
}
