package com.pangjie.jdbcBatch;


import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.service.UserInfoService;
import com.pangjie.springSecurity.annotation.WithoutToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/JDBCBatch")
public class JDBCBatchController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/test")
    @WithoutToken
    public void test() {
//        ArrayList<UserInfoCon> list = new ArrayList<>();
//        list.ensureCapacity(32000);
        List<UserInfo> all = userInfoService.findAll();
                JDBCBatch.jdbcBatch(all);
    }
}
