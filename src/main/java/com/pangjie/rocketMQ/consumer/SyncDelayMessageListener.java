package com.pangjie.rocketMQ.consumer;

import com.alibaba.fastjson.JSON;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/*/*
 * @Author PangJie___
 * @Description //TODO 监听消费同步发送的延时消息
 * @Date 下午2:45 16/3/2022
 * param
 * return
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "sync-delay-consumer", topic = "sync-delay-message")
public class SyncDelayMessageListener implements RocketMQListener<GoodsInfo> {
    @Override
    public void onMessage(GoodsInfo goodsInfo) {
        log.info("消费到了同步延时消息: message = {}", JSON.toJSONString(goodsInfo));
    }
}