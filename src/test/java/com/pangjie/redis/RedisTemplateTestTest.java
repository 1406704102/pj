package com.pangjie.redis;

import com.pangjie.PjApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PjApplication.class)
class RedisTemplateTestTest {
    @Autowired
    private Map<String, DefaultRedisScript<Boolean>> luaBean;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisTest() {
        List<String> table = new ArrayList<String>();
        List<String> key = new ArrayList<String>();

        table.add("11_like");
        table.add("11_like_count");
        key.add("1_1");
        key.add("1");
        Boolean result = stringRedisTemplate.execute(luaBean.get("likeLua"), table, key.toArray());
        System.out.println(result);
    }

    @Test
    public void redisTest2() {
        String key = "11_like";
        String keyCount = "11_like_count";
        String like = "1";

        stringRedisTemplate.execute(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                Object o = operations.opsForHash().get(key, "1_3");
                if (o == null) {
                    operations.opsForHash().put(key, "1_3", like);
                    operations.opsForValue().increment(keyCount, 1);
                } else {
                    String s = o.toString();
                    if (s.equals("1")) {
                        if (like.equals("0")) {
                            operations.opsForValue().increment(keyCount, -1);
                        }
                    } else {
                        operations.opsForValue().increment(keyCount, 1);
                    }
                    operations.opsForHash().put(key, "1_3", like);
                }
                //结束事务
                return operations.exec();
            }
        });

    }
}