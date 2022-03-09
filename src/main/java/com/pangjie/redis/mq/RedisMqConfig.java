package com.pangjie.redis.mq;

import com.pangjie.lottery.entiy.LotteryLog;
import io.lettuce.core.XGroupCreateArgs;
import io.lettuce.core.XReadArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConverters;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * redis 消息队列
 *
 * @author liaozesong
 */
@Slf4j
@Configuration
public class RedisMqConfig {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private OriginalConsumer originalConsumer;

    private static final String DEFAULT_TOPIC = "KafkaTopicConstant.DPC_C2_DATA_PARSER_SUCCESS";
    private static final String DEFAULT_GROUP = DEFAULT_TOPIC;
    private static final String LOTTERY_LOG = "LotteryLog";
    private static final String LOTTERY_LOG_GROUP = LOTTERY_LOG;

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, LotteryLog>> listener(RedisConnectionFactory connectionFactory) {
        //初始化topic
        try {
            LettuceConnection clusterConnection = (LettuceConnection) connectionFactory.getConnection();
            XReadArgs.StreamOffset<byte[]> streamOffset = XReadArgs.StreamOffset.from(LettuceConverters.toBytes(DEFAULT_GROUP), "0-0");
            clusterConnection.getNativeConnection().xgroupCreate(streamOffset, LettuceConverters.toBytes(DEFAULT_GROUP), XGroupCreateArgs.Builder.mkstream());
        } catch (Exception ex) {
            log.warn("Already Created {}", ex.getMessage());
        }


        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, LotteryLog>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ZERO)
                .batchSize(1)
                .targetType(LotteryLog.class)
                .executor(threadPoolTaskExecutor)
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, LotteryLog>> container = StreamMessageListenerContainer
                .create(connectionFactory, options);


        //指定消费者对象
        container.register(
                StreamMessageListenerContainer.StreamReadRequest.builder(StreamOffset.create(DEFAULT_TOPIC, ReadOffset.lastConsumed()))
                        .errorHandler((error) -> {
                            if (!(error instanceof QueryTimeoutException)) {
                                log.error(error.getMessage(), error);
                            }
                        })
                        .cancelOnError(e -> false)
                        .consumer(Consumer.from(DEFAULT_GROUP, DEFAULT_GROUP))
                        //关闭自动ack确认
                        .autoAcknowledge(false)
                        .build()
                , originalConsumer);
        container.start();
        return container;
    }


//    @Bean
//    public StreamMessageListenerContainer<String, ObjectRecord<String, LotteryLog>> listener2(RedisConnectionFactory connectionFactory) {
//        //初始化topic
//        try {
//            LettuceConnection clusterConnection = (LettuceConnection) connectionFactory.getConnection();
//            XReadArgs.StreamOffset<byte[]> streamOffset = XReadArgs.StreamOffset.from(LettuceConverters.toBytes(LOTTERY_LOG_GROUP), "0-0");
//            clusterConnection.getNativeConnection().xgroupCreate(streamOffset, LettuceConverters.toBytes(LOTTERY_LOG_GROUP), XGroupCreateArgs.Builder.mkstream());
//        } catch (Exception ex) {
//            log.warn("Already Created {}", ex.getMessage());
//        }
//
//
//        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, LotteryLog>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
//                .builder()
//                .pollTimeout(Duration.ZERO)
//                .batchSize(1)
//                .targetType(LotteryLog.class)
//                .executor(threadPoolTaskExecutor)
//                .build();
//
//        StreamMessageListenerContainer<String, ObjectRecord<String, LotteryLog>> container = StreamMessageListenerContainer
//                .create(connectionFactory, options);
//
//
//        //指定消费者对象
//        container.register(
//                StreamMessageListenerContainer.StreamReadRequest.builder(StreamOffset.create(LOTTERY_LOG, ReadOffset.lastConsumed()))
//                        .errorHandler((error) -> {
//                            if (!(error instanceof QueryTimeoutException)) {
//                                log.error(error.getMessage(), error);
//                            }
//                        })
//                        .cancelOnError(e -> false)
//                        .consumer(Consumer.from(LOTTERY_LOG_GROUP, LOTTERY_LOG_GROUP))
//                        //关闭自动ack确认
//                        .autoAcknowledge(false)
//                        .build()
//                , originalConsumer);
//        container.start();
//        return container;
//    }
}


