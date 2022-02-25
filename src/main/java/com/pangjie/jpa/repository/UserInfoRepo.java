package com.pangjie.jpa.repository;


import com.pangjie.jpa.entity.UserInfo;
import org.springframework.data.jpa.repository.*;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByUserName(String userName);


    /*
     * @Author pangjie
     * @Description //TODO 本方法使用对象进行类似原生sql 查询(?1 代表传入的第一个参数) (下一行的写法是使用原生sql进行查询)
     *                     @Query(nativeQuery = true,value = "select * from light_log  order by id desc limit 0 , 10")
     * @Date 10:30 2020/10/20 0020
     * @Param
     * @return
     */
    @Transactional
    @Modifying
    @Lock(LockModeType.WRITE)
    @Query("update UserInfo u set u.userName = '132' ")
    void updateLightLottery();
}
