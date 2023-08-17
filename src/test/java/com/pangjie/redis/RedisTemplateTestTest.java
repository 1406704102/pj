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

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PjApplication.class)
class RedisTemplateTestTest {
    @Autowired
    private Map<String, DefaultRedisScript<Boolean>> luaBean;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisTest() {
        List<String> table = new ArrayList<>();
        List<String> key = new ArrayList<>();
        table.add("11_like");
        table.add("11_like_count");
        key.add("did333");//点赞di
        key.add("tdid111");//被点赞id
        key.add("0");//点赞状态
        Boolean result = stringRedisTemplate.execute(luaBean.get("likeLua"), table, key.toArray());
    }

    @Test
    public void redisTest2() {
        String key = "11_like_2";
        String keyCount = "11_like_count_2";
        String like = "1";
        String likeId = "likeId333";
        String toLikeId = "toLikeId111";
        String mapKey = likeId + "_" + toLikeId;
        stringRedisTemplate.execute(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String mapKey = likeId + "_" + toLikeId;
                //开启事务
                operations.multi();

                if (like.equals("0")) {
                    operations.opsForHash().delete(key, mapKey);
                    operations.opsForHash().increment(keyCount, toLikeId, -1);
                } else {
                    operations.opsForHash().put(key, mapKey, like);
                    operations.opsForHash().increment(keyCount, toLikeId, 1);
                }


                //结束事务
                return operations.exec();
            }
        });

    }

    @Test
    public void redisTest3() {
        String key = "11_like";
        String keyCount = "11_like_count";
        String like = "1";

        stringRedisTemplate.execute(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                //开启事务之后不去不到值
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

    @Test
    public void redisLottery() {
        String goodsHashKey = "goodsHash";
        String probabilityHashKey = "probabilityHash";

        int anInt = new Random().nextInt(9900) + 100;

        Set<Object> keys = stringRedisTemplate.opsForHash().keys(probabilityHashKey);
        List<Object> sorted = keys.stream().sorted().collect(Collectors.toList());

        int i = anInt / 100;

        //获取有库存且小于抽奖次数的
        List<Object> collect = sorted.stream().filter(f -> {
            int i1 = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForHash().get(probabilityHashKey, f)).toString());
            return Integer.parseInt(f.toString()) < i * 100 && i1 > 0;
        }).collect(Collectors.toList());

        //谢谢参与
        if (collect.size() == 0) {
            return;
        }
        //减库存
        int index = this.reduce(collect.size(), collect);
        Object goodsInfo =  stringRedisTemplate.opsForHash().get(goodsHashKey, collect.get(index-1));
        System.out.println(goodsInfo);
    }

    public int reduce(int index,List<Object> collect) {
        String probabilityHashKey = "probabilityHash";
        if (index == 0) {
            return 0;
        }
        Long o = stringRedisTemplate.opsForHash().increment(probabilityHashKey, collect.get(index-1), -1);
        //最后一个倍抽走,奖品变为下一个
        if (o < 0) {
            this.reduce(index - 1, collect);
        } else {
            return index;
        }
        return 0;
    }

    @Test
    public void tt() {
        for (int i = 0; i < 10; i++) {
            this.redisLottery();
        }
    }
}