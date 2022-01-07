package com.pangjie.jpa.service.impl;


import com.pangjie.doubleDBConfig.DataSource;
import com.pangjie.doubleDBConfig.DataSourceNames;
import com.pangjie.jpa.config.QueryHelp;
import com.pangjie.springSecurity.JwtTokenUtil;
import com.pangjie.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.JoinSequence;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.repository.UserInfoRepo;
import com.pangjie.jpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "data")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public void save(UserInfo userInfo) {
        userInfoRepo.save(userInfo);
    }

    @Override
    @DataSource(DataSourceNames.TWO)
    @Cacheable(key = "'user:' + #p0")
    public UserInfo findById2(Integer userId) {
        Optional<UserInfo> byId = userInfoRepo.findById(userId);
        return byId.get();
    }

    @Override
    @Cacheable(key = "'user:'+userId")
    public UserInfo findById(Integer userId) {
        Optional<UserInfo> byId = userInfoRepo.findById(userId);
        return byId.get();
    }

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepo.findByUserName(openid);
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
//    @Cacheable(value = "userInfo", key = "'user:'")
    public Map<String, Object> queryAll(UserInfo userInfo, Pageable pageable) {
        Page<UserInfo> all = userInfoRepo.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, userInfo, criteriaBuilder);
            return predicate;
        }, pageable);
//        Predicate predicate = QueryHelp.getPredicate(root, userInfo, criteriaBuilder);

//        Page<UserInfo> all = userInfoRepo.findAll((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), "1"), pageable);
        return PageUtil.toPage(all);
    }

    @Override
    public UserInfo findByUserName(String username) {
        return userInfoRepo.findByUserName(username);
    }

    @Override
    public Map<String, Object> login(UserInfo userInfo) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUserName());
            if (!passwordEncoder.matches(userInfo.getPassWord(), userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", tokenHead + " " + token);
        return data;
    }

    @Override
    public UserInfo register(UserInfo userInfo) {
        UserInfo newUser = new UserInfo();
        BeanUtils.copyProperties(userInfo, newUser);
        //查询是否有相同用户名的用户
        UserInfo byUserName = userInfoRepo.findByUserName(newUser.getUserName());
        if (byUserName != null) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(userInfo.getPassWord());
        newUser.setPassWord(encodePassword);
        return userInfoRepo.save(newUser);
    }
}
