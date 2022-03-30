package com.pangjie.lottery.controller;


import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.lottery.sevice.GoodsInfoService;
import com.pangjie.rocketMQ.producer.RocketTemplateProducer;
import com.pangjie.springSecurity.annotation.WithoutToken;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/goodsInfo")
@AllArgsConstructor
@Log4j2
public class GoodsInfoController {

    private final GoodsInfoService goodsInfoService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RocketTemplateProducer rocketTemplateProducer;

    @PostMapping("/doLottery")
    @WithoutToken
    public ResponseEntity<Object> doLottery(int userId) {
//        List<GoodsInfo> goodsInfos = goodsInfoService.findAll();
        int i = new Random().nextInt(3);
//        System.out.println("抽中了:" + goodsInfos.get(i).getGoodsName());
        synchronized (this) {
            goodsInfoService.reduceStock(userId);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/doLottery2")
    @WithoutToken
    public ResponseEntity<Object> doLottery2(int userId) {
        //重试次数
        int reGetTimes = 4;
        //重试等待时间
        long reGetTime = 1000;
        Boolean aBoolean = false;
        try {
            //重试
            while (reGetTimes > 0) {
                //尝试拿锁(6秒后自动释放)
                aBoolean = redisTemplate.opsForValue().setIfAbsent("Lottery" + userId, userId, 6, TimeUnit.SECONDS);
                //拿到到锁
                if (aBoolean) break;
                reGetTimes--;
                //等待时间
                Thread.sleep(reGetTime);
            }
            //如果拿到锁
            if (!aBoolean) {
                List<GoodsInfo> all = goodsInfoService.findAll();
                all.forEach(f -> {
                    GoodsInfo goodsInfo = (GoodsInfo) redisTemplate.opsForValue().get("Lottery:goodsInfo:" + f.getId());
                    System.out.println(goodsInfo);
                    if (goodsInfo == null) {
                        redisTemplate.opsForValue().set("Lottery:goodsInfo:" + f.getId(), f);
                        redisTemplate.opsForValue().set("Lottery:goodsStock:" + f.getId(), f.getGoodsStock());
                    } else return;
                });
                goodsInfoService.reduceStock2(userId);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("稍后再试", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info(e);
        } finally {
            //删除锁
            redisTemplate.delete("Lottery" + userId);
        }
        return new ResponseEntity<>("稍后再试", HttpStatus.OK);

    }

    @GetMapping("/{msg}")
    @WithoutToken
    public void test(@PathVariable String msg) {
        rocketTemplateProducer.sendMessage("test-topic-1", msg);
    }

}
