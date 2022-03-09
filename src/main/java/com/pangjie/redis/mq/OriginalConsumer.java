package com.pangjie.redis.mq;

import com.pangjie.eventListener.LotteryLogEvent;
import com.pangjie.lottery.entiy.LotteryLog;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OriginalConsumer implements StreamListener<String, ObjectRecord<String, LotteryLog>> {
    private final StringRedisTemplate stringRedisTemplate;
    
    @Override
    @SneakyThrows
    public void onMessage(ObjectRecord<String, LotteryLog> record) {
        LotteryLog message = record.getValue();
        try {
            log.info("1.监听到消息[{}]", message);
        } catch (Exception e) {
            log.warn("listen msg error：json:{}", message);
            log.warn(e.getMessage(), e);
        } finally {
//            this.stringRedisTemplate.opsForStream().acknowledge("KafkaTopicConstant.DPC_C2_DATA_PARSER_SUCCESS", record);
        }
    }
}

