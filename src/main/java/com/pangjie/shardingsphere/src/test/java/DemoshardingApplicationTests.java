package com.pangjie.shardingsphere.src.test.java;

import com.pangjie.*;
import com.pangjie.shareding.Sharding;
import com.pangjie.shareding.ShardingBase;
import com.pangjie.shareding.ShardingBaseDao;
import com.pangjie.shareding.ShardingDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
class DemoshardingApplicationTests {

    @Autowired
    private ShardingDao shardingDao;

    @Autowired
    private ShardingBaseDao shardingBaseDao;

    @Test
//    @Transactional
    public void addTest() {
        for (int i = 0; i < 10; i++) {
            Sharding sharding = new Sharding();
            sharding.setName("java");
            sharding.setUserId(100L);
            sharding.setStatus("Normal");
            shardingDao.insert(sharding);
        }
        ShardingBase shardingBase = new ShardingBase();
        shardingBase.setName("java");
        shardingBase.setUserId(100L);
        shardingBase.setStatus("Normal");
        shardingBaseDao.insert(shardingBase);
    }

    @Test
    public void selectTest() {
        System.out.println(shardingDao.selectById(1687396130637991937L));
    }
}