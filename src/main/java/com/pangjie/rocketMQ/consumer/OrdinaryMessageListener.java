package com.pangjie.rocketMQ.consumer;

import com.alibaba.fastjson.JSON;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "ordinary-consumer", topic = "ordinary-message")
public class OrdinaryMessageListener implements RocketMQListener<GoodsInfo> {
    @Override
    public void onMessage(GoodsInfo goodsInfo) {
        log.info("消费到了普通消息: message = {}", JSON.toJSONString(goodsInfo));

    }
}
