package com.pangjie.jpa.repository;


import com.pangjie.jpa.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByOpenid(String openid);


    /*
     * @Author pangjie
     * @Description //TODO 本方法使用对象进行类似原生sql 查询 (下一行的写法是使用原生sql进行查询)
     *                     @Query(nativeQuery = true,value = "select * from light_log  order by id desc limit 0 , 10")
     * @Date 10:30 2020/10/20 0020
     * @Param
     * @return
     */
    @Transactional
    @Modifying
    @Query("update UserInfo u set u.isLight = 0, u.isLottery = 0 ")
    void updateLightLottery();
}
