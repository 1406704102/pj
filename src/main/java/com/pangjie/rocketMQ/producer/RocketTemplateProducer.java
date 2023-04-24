package com.pangjie.rocketMQ.producer;

import com.alibaba.fastjson.JSON;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.lottery.entiy.GoodsInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
 * @Author pangjie
 * @Description //TODO rocketMQ 发送消息
 * @Date 下午4:10 15/3/2022
 * @Param
 * @return
 */
@Service
@Slf4j
@AllArgsConstructor
public class RocketTemplateProducer {
    private final RocketMQTemplate rocketMQTemplate;

    /*/*
     * @Author PangJie___
     * @Description //TODO 发送普通消息
     * @Date 下午4:27 15/3/2022
     * param [topic, message] 主题,消息内容
     * return void
     */
    public void sendMessage(String topic, Object message) {
        rocketMQTemplate.convertAndSend(topic, message);
        log.info("普通消息发送完成:message={}", message);
    }

    /*/*
     * @Author PangJie___
     * @Description //TODO  发送同步消息
     * @Date 下午4:33 15/3/2022
     * param [topic, message]
     * return void
     */
    public void syncSendMessage(String topic, Object message) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        log.info("同步消息发送完成:msg={},sendResult={}", message, sendResult);
    }

    /*/*
     * @Author PangJie___
     * @Description //TODO 发送异步消息
     * @Date 下午4:35 15/3/2022
     * param [topic, message]
     * return void
     */
    public void asyncSendMessage(String topic, Object message) {
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息发送完成:message={},sendResult={}", message, sendResult.getSendStatus());
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("异步消息发送异常，exception = {}", throwable.getMessage());
            }
        });
    }

    /*/*
     * @Author PangJie___
     * @Description //TODO 发送单向消息
     * @Date 下午4:39 15/3/2022
     * param [topic, message]
     * return void
     */
    public void sendOneWayMessage(String topic, Object message) {
        rocketMQTemplate.sendOneWay(topic, message);
        log.info("单向消息发送完成:message={}", message);
    }
    /*/*
     * @Author PangJie___
     * @Description //TODO 同步发送批量消息
     * @Date 下午5:22 15/3/2022
     * param [topic, messageList, timeout] 主题,消息列表,超时时间
     * return void
     */
    public void syncSendMessages(String topic, List<Message<?>> messageList, long timeout) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, messageList, timeout);
        log.info("同步发送批量消息完成：message = {}", JSON.toJSONString(messageList));
    }

    /*/*
     * @Author PangJie___
     * @Description //TODO 发送事务消息
     * @Date 下午5:23 15/3/2022
     * param [topic, message]
     * return void
     */
    public void sendMessageInTransaction(String topic, GoodsInfo message) {
        String transactionId = UUID.randomUUID().toString();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(topic, MessageBuilder.withPayload(message)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                .build(), message);
        log.info("发送事务消息（半消息）完成：result = {}", result);
    }

    /*/*
     * @Author PangJie___发送携带 tag 的消息（过滤消息）
     * @Description //TODO
     * @Date 下午5:23 15/3/2022
     * param [topic, message]
     * topic，RocketMQTemplate将 topic 和 tag 合二为一了，底层会进行
     *                拆分再组装。只要在指定 topic 时跟上 {:tags} 就可以指定tag
     *                例如 test-topic:tagA
     *
     * return
     */
    /**
     * 发送携带 tag 的消息（过滤消息）
     *
     * @param topic
     * @param message 消息体
     */
    public void syncSendMessageWithTag(String topic, Object message) {
        rocketMQTemplate.syncSend(topic, message);
        log.info("发送带 tag 的消息完成：message = {}", message);
    }

    /**
     * 同步发送延时消息
     *
     * @param topic      topic
     * @param message    消息体
     * @param timeout    超时
     * @param delayLevel 延时等级：现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，
     *                   从1s到2h分别对应着等级 1 到 18，消息消费失败会进入延时消息队列
     *                   "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
     */
    public void syncSendDelay(String topic, Object message, long timeout, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), timeout, delayLevel);
        log.info("已同步发送延时消息 message = {}", message);
    }

    /**
     * 异步发送延时消息
     *
     * @param topic      topic
     * @param message    消息对象
     * @param timeout    超时时间
     * @param delayLevel 延时等级
     */
    public void asyncSendDelay(String topic, Object message, long timeout, int delayLevel) {
        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(message).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步发送延时消息成功，message = {}", message);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("异步发送延时消息发生异常，exception = {}", throwable.getMessage());
            }
        }, timeout, delayLevel);
        log.info("已异步发送延时消息 message = {}", message);
    }

    /**
     * 发送单向顺序消息
     *
     * @param topic topic
     */
    public void sendOneWayOrderly(String topic) {
        for (int i = 0; i < 30; i++) {
            rocketMQTemplate.sendOneWayOrderly(topic, MessageBuilder.withPayload("message - " + i).build(), "topic");
            log.info("单向顺序发送消息完成：message = {}", "message - " + i);
        }
    }

    /**
     * 同步发送顺序消息
     *
     * @param topic topic
     */
    public void syncSendOrderly(String topic) {
        for (int i = 0; i < 30; i++) {
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, MessageBuilder.withPayload("message - " + i).build(), "syncOrderlyKey");
            log.info("同步顺序发送消息完成：message = {}, sendResult = {}", "message - " + i, sendResult);
        }
    }


}
