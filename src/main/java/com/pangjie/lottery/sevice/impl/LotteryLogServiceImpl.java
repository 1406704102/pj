package com.pangjie.lottery.sevice.impl;

import com.pangjie.lottery.eventListener.LotteryLogEvent;
import com.pangjie.lottery.entiy.LotteryLog;
import com.pangjie.lottery.repository.LotteryLogRepo;
import com.pangjie.lottery.sevice.LotteryLogService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LotteryLogServiceImpl implements LotteryLogService {

    private final LotteryLogRepo lotteryLogRepo;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String,Object> redisTemplate;

    public LotteryLog add(LotteryLog lotteryLog) {
        return lotteryLogRepo.save(lotteryLog);
    }


    @EventListener
    public void create(LotteryLogEvent logEvent) {
//
//        StringRecord stringRecord = StreamRecords.string(Collections.singletonMap("name", "kb")).withStreamKey("LotteryLog");
//        RecordId add = stringRedisTemplate.opsForStream().add(stringRecord);

//        ObjectRecord<String, LotteryLogEvent> record = StreamRecords.newRecord()
//                .in("LotteryLog")
//                .ofObject(logEvent)
//                .withId(RecordId.autoGenerate());
//
//        RecordId recordId = redisTemplate.opsForStream()
//                .add(record);

        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setGoodsId(logEvent.getGoodsId());
        lotteryLog.setUserId(logEvent.getUserId());
        lotteryLogRepo.save(lotteryLog);
        try {
            ObjectRecord<String, LotteryLog> record = StreamRecords.objectBacked(lotteryLog).withStreamKey("KafkaTopicConstant.DPC_C2_DATA_PARSER_SUCCESS");
            RecordId recordId = stringRedisTemplate.opsForStream().add(record);
            System.out.println("消息发送成功:"+recordId);
        } catch (Exception e) {
            throw new RuntimeException("消息发送失败[{}]");
        }


    }
}
