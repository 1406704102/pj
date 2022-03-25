package com.pangjie.rocketMQ.consumer;


import com.alibaba.fastjson.JSON;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/*/*
 * @Author PangJie___
 * @Description //TODO  监听消费同步批量发送的消息。TIPS: 指定 @RocketMQMessageListener 的属性 consumeMode = ConsumeMode.ORDERLY
 * 可以在消费消息时保证消费顺序，与生产消息集合时添加到集合中的顺序一致代价是只会创建一个消费者线程消费消息，效率偏低
 * @Date 下午2:41 16/3/2022
 * param
 * return
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "syncs-consumer", topic = "sync-messages2")
public class SyncMessagesListener implements RocketMQListener<GoodsInfo> {
    @Override
    public void onMessage(GoodsInfo goodsInfo) {
        log.info("消费到了同步批量消息之: message = {}", JSON.toJSONString(goodsInfo));
    }
}