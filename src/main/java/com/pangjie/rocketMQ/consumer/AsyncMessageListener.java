package com.pangjie.rocketMQ.consumer;

import com.alibaba.fastjson.JSON;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


/*/*
 * @Author PangJie___
 * @Description //TODO 监听消费异步发送的消息
 * @Date 下午3:07 16/3/2022
 * param 
 * return 
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "async-consumer", topic = "async-message")
public class AsyncMessageListener implements RocketMQListener<GoodsInfo> {
    @Override
    public void onMessage(GoodsInfo goodsInfo) {
        try {
            log.info("消费到了异步消息: message = {}", JSON.toJSONString(goodsInfo));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}