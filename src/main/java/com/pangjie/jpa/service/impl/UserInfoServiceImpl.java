package com.pangjie.jpa.service.impl;


import com.pangjie.doubleDBConfig.annotation.DataSource;
import com.pangjie.doubleDBConfig.annotation.DataSourceNames;
import com.pangjie.jpa.config.QueryHelp;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.repository.UserInfoRepo;
import com.pangjie.jpa.service.UserInfoService;
import com.pangjie.springSecurity.JwtTokenUtil;
import com.pangjie.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@CacheConfig(cacheNames = "data")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AuthenticationManager  authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

//    private UserInfoServiceImpl getProxy(){
//        return SpringUtils.getBean(this.getClass());
//    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepo.save(userInfo);
    }

    @Override
    @DataSource(DataSourceNames.TWO)
//    @Transactional(rollbackFor = Exception.class)
    public UserInfo save2(UserInfo userInfo) {
        UserInfo save = userInfoRepo.save(userInfo);
        return save;
    }

        @Transactional(rollbackFor = Exception.class)
//    @DataSource(DataSourceNames.TWO)
    public List<UserInfo> saveUsers(UserInfo userInfo) {
        UserInfoServiceImpl bean = applicationContext.getBean(UserInfoServiceImpl.class);
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo save = bean.save(userInfo);
        userInfos.add(save);
//        int i = 1 / 0;
        UserInfo e = bean.save2(userInfo);
        userInfos.add(e);
        return userInfos;
    }

    @Override
//    @DataSource(DataSourceNames.TWO)
    @Cacheable(key = "'user:' + #p0")
    public UserInfo findById2(Integer userId) {
        Optional<UserInfo> byId = userInfoRepo.findById(userId);
        return byId.get();

    }

    @Override
//    @Cacheable(key = "'user:'+#p0")
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
//        Page<UserInfo> all = userInfoRepo.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, userInfo, criteriaBuilder), pageable);
        Page<UserInfo> all = userInfoRepo.findAll((Specification<UserInfo>) (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, userInfo, criteriaBuilder), pageable);
//        userInfoRepo.findAll(new Specification<UserInfo>() {
//            @Override
//            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                return null;
//            }
//        });
        return PageUtil.toPage(all);
    }

    @Override
    public UserInfo findByUserName(String username) {
        return userInfoRepo.findByUserName(username);
    }

    @Override
    @DataSource(DataSourceNames.ONE)
    public UserInfo findByUserName2(String username) {
        return userInfoRepo.findByUserName(username);
    }

    @Override
    public Map<String, Object> login(UserInfo userInfo) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUserName());
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

//            if (!passwordEncoder.matches(userInfo.getPassWord(), userDetails.getPassword())) {
//                throw new BadCredentialsException("密码不正确");
//            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
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
        UserInfo newUser = UserInfo.builder().build();
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

    @Override
    public List<UserInfo> findByUserNameOfInside(String userName) {
//        UserInfo byUserName2 = this.getProxy().findByUserName2(userName);
//        UserInfo byUserName = this.getProxy().findByUserName(userName);
        ArrayList<UserInfo> list = new ArrayList<>();
//        list.add(byUserName);
//        list.add(byUserName2);
        return list;
    }

}
