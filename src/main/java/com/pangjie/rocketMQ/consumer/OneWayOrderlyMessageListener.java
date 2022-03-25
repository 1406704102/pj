package com.pangjie.rocketMQ.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


/*/*
 * @Author PangJie___
 * @Description //TODO 消费单向有序消息
 * TIPS: @RocketMQMessageListener 必须指定属性
 *       consumeMode = ConsumeMode.ORDERLY 才能顺序消费消息
 * @Date 下午2:45 16/3/2022
 * param 
 * return 
 */
@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "oneWay-order-consumer", topic = "oneWay-order-message", consumeMode = ConsumeMode.ORDERLY)
public class OneWayOrderlyMessageListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("消费到了单向有序消息: message = {}", s);
    }
}