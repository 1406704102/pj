package com.pangjie.rocketMQ.controller;

import com.pangjie.PjApplication;
import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.rocketMQ.producer.RocketTemplateProducer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PjApplication.class)
class RocketTemplateControllerTest {

    @Autowired
    private RocketTemplateProducer producer;

    @Test
    public void SyncSendMessage() {
        GoodsInfo goodsInfo = GoodsInfo.builder()
                .goodsName("goodsName-sync")
                .goodsStock(100)
                .build();
        this.producer.syncSendMessage("sync-message", goodsInfo);
    }
}