package com.pangjie.rocketMQ.consumer;

import com.alibaba.fastjson.JSON;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "tag-consumer", topic = "tag-message",
        selectorExpression = "testTag", selectorType = SelectorType.TAG
)
public class TagMessageListener implements RocketMQListener<GoodsInfo> {
    @Override
    public void onMessage(GoodsInfo goodsInfo) {
        log.info("消费到了带 tag 的消息: tag = {}, message = {}", "testTag", JSON.toJSONString(goodsInfo));
    }
}