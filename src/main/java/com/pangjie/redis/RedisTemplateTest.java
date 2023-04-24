package com.pangjie.redis;

import com.pangjie.springSecurity.annotation.WithoutToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Map<String, DefaultRedisScript<Boolean>> luaBean;


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

        List<String> keys = new ArrayList<>();
        stringRedisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(
                    ScanOptions.scanOptions()
                            .count(Long.MAX_VALUE)
                            .match("*-talk-")
                            .build()
            )) {
                cursor.forEachRemaining(item -> keys.add(RedisSerializer.string().deserialize(item)));
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/redisLua")
//    @PreAuthorize("@roleCheck.check('1:首页')")
    @WithoutToken
    public ResponseEntity<Object> redisLua() {
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        keys.add("key1");
        keys.add("key2");
        values.add("1");
        values.add("2");
        Boolean result = stringRedisTemplate.execute(luaBean.get("lua1"), keys, values.toArray());
        Boolean result2 = stringRedisTemplate.execute(luaBean.get("lua2"), keys, values.toArray());
        System.out.println(result);
        System.out.println(result2);
        return null;
    }
}
