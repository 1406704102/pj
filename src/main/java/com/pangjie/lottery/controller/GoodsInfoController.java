package com.pangjie.lottery.controller;


import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.lottery.sevice.GoodsInfoService;
import com.pangjie.springSecurity.annotation.WithoutToken;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/goodsInfo")
@AllArgsConstructor
public class GoodsInfoController {

    private final GoodsInfoService goodsInfoService;
    private final RedisTemplate<String,Object> redisTemplate;

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
//        List<GoodsInfo> all = goodsInfoService.findAll();
//        all.forEach(f->{
//            GoodsInfo goodsInfo = (GoodsInfo) redisTemplate.opsForValue().get("Lottery:goodsInfo:" + f.getId());
//            System.out.println(goodsInfo);
//            if (goodsInfo == null) {
//                redisTemplate.opsForValue().set("Lottery:goodsInfo:" + f.getId(), f);
//                redisTemplate.opsForValue().set("Lottery:goodsStock:" + f.getId(), f.getGoodsStock());
//            } else return;
//        });
        goodsInfoService.reduceStock2(userId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
