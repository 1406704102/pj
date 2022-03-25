package com.pangjie.rocketMQ.consumer;

import com.alibaba.fastjson.JSON;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/*/*
 * @Author PangJie___
 * @Description //TODO 监听消费事务消息
 * @Date 下午3:07 16/3/2022
 * param 
 * return 
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "transaction-consumer", topic = "transaction-message")
public class TransactionMessageListener implements RocketMQListener<GoodsInfo> {
    @Override
    public void onMessage(GoodsInfo goodsInfo) {
        log.info("消费到了事务消息: message = {}", JSON.toJSONString(goodsInfo));
    }
}