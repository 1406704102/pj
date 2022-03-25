package com.pangjie.rocketMQ.controller;

import com.alibaba.fastjson.JSON;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.rocketMQ.producer.RocketTemplateProducer;
import com.pangjie.springSecurity.annotation.WithoutToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用 RocketMQTemplate 发送各种消息
 */
@Slf4j
@RestController
@RequestMapping("/rocket/producer")
@AllArgsConstructor
public class RocketTemplateController {
    private final RocketTemplateProducer producer;

    /**
     * 发送普通消息
     */
    @GetMapping("/ordinary")
    @WithoutToken
    public String sendMessage() {
        GoodsInfo goodsInfo = GoodsInfo.builder()
                .goodsName("goodsName-ordinary")
                .goodsStock(100)
                .build();
        this.producer.sendMessage("ordinary-message", goodsInfo);
        return "success：消息已发送：message = " + JSON.toJSONString(goodsInfo);
    }

    /**
     * 同步发送消息
     *
     * @return 反馈信息
     */
    @GetMapping("/sync")
    @WithoutToken
    public String syncSendMessage() {
        GoodsInfo goodsInfo = GoodsInfo.builder()
                .goodsName("goodsName-sync")
                .goodsStock(100)
                .build();
        this.producer.syncSendMessage("sync-message", goodsInfo);
        return "success：消息已同步发送：message = " + JSON.toJSONString(goodsInfo);
    }

    /**
     * 异步发送消息
     *
     * @return 反馈信息
     */
    @GetMapping("/async")
    @WithoutToken
    public String asyncSendMessage() {
        GoodsInfo goodsInfo = GoodsInfo.builder()
                .goodsName("goodsName-async")
                .goodsStock(100)
                .build();
        this.producer.asyncSendMessage("async-message", goodsInfo);
        return "success：消息已异步发送：message = " + JSON.toJSONString(goodsInfo);
    }

    /**
     * 发送批量消息
     *
     * @return 反馈信息
     */
    @GetMapping("/syncMessages")
    @WithoutToken
    public String asyncSendMessages() {
        List<Message<?>> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messageList.add(MessageBuilder.withPayload(
                    GoodsInfo.builder()
                            .id(i)
                            .goodsName("goodsName-syncMessages")
                            .goodsStock(i * 100)
                            .build()
            ).build());
        }
        this.producer.syncSendMessages("sync-messages2", messageList, 5000);
        return "success：已异步批量发送消息";
    }

    /**
     * 发送事务消息
     *
     * @return 反馈信息
     */
    @GetMapping("/transactionMessage")
    @WithoutToken
    public String sendMessageInTransactionMessage() {
        GoodsInfo goodsInfo = GoodsInfo.builder()
                .goodsName("goodsName-message")
                .goodsStock(100)
                .build();
        this.producer.sendMessageInTransaction("transaction-message", goodsInfo);
        return "success：已发送事务消息：message = " + JSON.toJSONString(goodsInfo);
    }

    /**
     * 单向发送消息
     *
     * @return 反馈信息
     */
    @GetMapping("/oneWay")
    @WithoutToken
    public String oneWaySendMessage() {
        GoodsInfo goodsInfo = GoodsInfo.builder()
                .goodsName("goodsName-oneWay")
                .goodsStock(100)
                .build();
        this.producer.sendOneWayMessage("oneWay-message", goodsInfo);
        return "success：消息已单向发送：message = " + JSON.toJSONString(goodsInfo);
    }

    /**
     * 单向发送顺序消息
     *
     * @return 反馈信息
     */
    @GetMapping("/oneWayOrderly")
    @WithoutToken
    public String sendOneWayOrderlyMessage() {
        this.producer.sendOneWayOrderly("oneWay-order-message");
        return "success：已单向发送有序消息.. ";
    }

    /**
     * 同步发送顺序消息
     *
     * @return 反馈信息
     */
    @GetMapping("/syncOrderly")
    @WithoutToken
    public String syncSendOrderlyMessage() {
        this.producer.syncSendOrderly("sync-order-message");
        return "success：已同步发送有序消息.. ";
    }

    /**
     * 同步发送延时消息
     *
     * @return 反馈信息
     */
    @GetMapping("/syncDelay")
    @WithoutToken
    public String syncSendDelayMessage() {
        UserInfo userDto = UserInfo.builder()
                .userName("sync")
                .passWord("delay.5788@gmail.com")
                .build();
        this.producer.syncSendDelay("sync-delay-message", userDto, 10000, 4);
        return "success：已同步发送延时消息.. ";
    }

    /**
     * 发送携带tag的消息，以便进行消息过滤
     *
     * @return 反馈信息
     */
    @GetMapping("/withTag")
    @WithoutToken
    public String syncSendWithTagMessage() {
        UserInfo userDto = UserInfo.builder()
                .userName("withTag")
                .passWord("17898097654")
                .build();
        this.producer.syncSendMessageWithTag("tag-message:testTag", userDto);
        return "success：已同步发送带 tag 的消息消息.. ";
    }
}
