package com.pangjie.jpa.controller;


import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.service.UserInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/userInfo")
public class UserInfoCon {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id) {
        UserInfo byId = userInfoService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> query(UserInfo userInfo, Pageable pageable) {
        Page<UserInfo> pages = userInfoService.queryAll(userInfo, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }
}
