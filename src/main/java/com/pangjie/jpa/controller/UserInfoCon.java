package com.pangjie.jpa.controller;


import com.pangjie.jpa.entity.MenuInfo;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.service.MenuInfoService;
import com.pangjie.jpa.service.UserInfoService;
import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.lottery.entiy.LotteryLog;
import com.pangjie.lottery.sevice.GoodsInfoService;
import com.pangjie.lottery.sevice.LotteryLogService;
import com.pangjie.springSecurity.annotation.WithoutToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/userInfo")
@AllArgsConstructor
public class UserInfoCon {

    private final UserInfoService userInfoService;
    private final MenuInfoService menuInfoService;
    private final LotteryLogService lotteryLogService;
    private final GoodsInfoService goodsInfoService;
    static int size;

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id) {
        UserInfo byId = userInfoService.findById2(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping("/findById")
    @WithoutToken
    public ResponseEntity<Object> findById(Integer id) {
        UserInfo byId = userInfoService.findById2(id);
        LinkedList<Object> objects = new LinkedList<>();

        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PutMapping("/add")
    @PreAuthorize("@roleCheck.check('1:系统设置')")
    public ResponseEntity<Object> add(@RequestBody UserInfo userInfo) {
        return new ResponseEntity<>(userInfoService.save(userInfo),HttpStatus.CREATED);
    }

    @PutMapping("/add2")
    public ResponseEntity<Object> add2(@RequestBody UserInfo userInfo) {
        return new ResponseEntity<>(userInfoService.save2(userInfo),HttpStatus.CREATED);
    }

    @PutMapping("/add3")
//    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> add3(@RequestBody UserInfo userInfo) {
//        userInfoService.save(userInfo);
//        userInfoService.save2(userInfo);
        ;
//        userInfoService.save2(userInfo);
//        UserInfo byUserName2 = userInfoService.findByUserName2(userInfo.getUserName());
//        List<GoodsInfo> all = goodsInfoService.findAll();
//        UserInfo userInfo2 = this.findByUserName2(userInfo.getUserName());
//        UserInfo userInfo1 = this.findByUserName(userInfo.getUserName());
////        UserInfo userInfo2 = userInfoService.findByUserName(userInfo.getUserName());
//        List list = new ArrayList();
//        list.add(userInfo1);
//        list.add(userInfo2);
//        MenuInfo menuInfo = menuInfoService.findById(Integer.valueOf(userInfo.getUserName()));
        return new ResponseEntity<>(userInfoService.saveUsers(userInfo),HttpStatus.CREATED);
    }

    public UserInfo findByUserName(String username) {
        return userInfoService.findByUserName(username);
    }
    public UserInfo findByUserName2(String username) {
        return userInfoService.findByUserName2(username);
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

    @GetMapping("/code")
//    @PreAuthorize("@roleCheck.check('1:首页')")
    public void getCode(@RequestParam("code") String code) {
        System.out.println(code);
    }

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 110; i++) {
            list.add(i);
        }
        System.out.println(size);
        ArrayList<Object> list2 = new ArrayList<>();
        list2.add(1);
        list2.addAll(list);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(null, null);
        HashSet<String> hashSet = new HashSet<>();
//        hashMap.put();
        hashSet.add("1");
        hashSet.add("1");
        TreeMap<Integer, Object> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.put(1, 1);
        System.out.println(treeMap);
    }
}
