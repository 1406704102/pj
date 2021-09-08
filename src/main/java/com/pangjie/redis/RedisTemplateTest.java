package com.pangjie.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    public void stringTemplate() {
        //向redis里存入数据和设置缓存时间
        stringRedisTemplate.opsForValue().set("key", "100", 60 * 10, TimeUnit.SECONDS);
        //val做-1操作
        stringRedisTemplate.boundValueOps("key").increment(-1);
        //根据key获取缓存中的val
        stringRedisTemplate.opsForValue().get("key");
        //val +1
        stringRedisTemplate.boundValueOps("key").increment(1);
        //根据key获取过期时间
        stringRedisTemplate.getExpire("key");
        //根据key获取过期时间并换算成指定单位
        stringRedisTemplate.getExpire("key", TimeUnit.SECONDS);
        //根据key删除缓存
        stringRedisTemplate.delete("key");
        //检查key是否存在，返回boolean值
        stringRedisTemplate.hasKey("key");
        //向指定key中存放set集合
        stringRedisTemplate.opsForSet().add("key", "1", "2", "3");
        //设置过期时间
        stringRedisTemplate.expire("key", 1000, TimeUnit.MILLISECONDS);
        //根据key查看集合中是否存在指定数据
        stringRedisTemplate.opsForSet().isMember("key", "1");
        //根据key获取set集合
        stringRedisTemplate.opsForSet().members("key");
        //验证有效时间
        Long expire = redisTemplate.boundHashOps("key").getExpire();
        System.out.println("redis有效时间：" + expire + "S");
    }
}
