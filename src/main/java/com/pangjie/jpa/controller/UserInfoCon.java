package com.pangjie.jpa.controller;


import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.service.UserInfoService;
import com.pangjie.springSecurity.annotation.WithoutToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/userInfo")
public class UserInfoCon {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id) {
        UserInfo byId = userInfoService.findById2(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    @WithoutToken
    public ResponseEntity<Object> query(UserInfo userInfo, Pageable pageable) {
        Map<String, Object> pages = userInfoService.queryAll(userInfo, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserInfo userInfo) {
        return new ResponseEntity<>(userInfoService.login(userInfo), HttpStatus.OK);
    }
    @PostMapping(value = "register")
    public ResponseEntity<Object> register(@RequestBody UserInfo userInfo) {
        UserInfo user = userInfoService.register(userInfo);
        if (user == null) {
            return new ResponseEntity<>("注册失败,用户名已存在",HttpStatus.FOUND);
        }
        return new ResponseEntity<>(userInfo,HttpStatus.CREATED);

    }
}
