package com.pangjie.lottery.sevice.impl;

import com.pangjie.eventListener.LotteryLogEvent;
import com.pangjie.jpa.entity.UserInfo;
import com.pangjie.jpa.repository.UserInfoRepo;
import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.lottery.repository.GoodsInfoRepo;
import com.pangjie.lottery.sevice.GoodsInfoService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private final GoodsInfoRepo goodsInfoRepo;
    private final UserInfoRepo userInfoRepo;
    private final RedisTemplate<String,Object> redisTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<GoodsInfo> findAll() {
        return goodsInfoRepo.findAll();
    }

    @Override
    @Transactional
    public void reduceStock(int userId) {
        ArrayList<Integer> old = new ArrayList<>();
        old.add(-1);
        GoodsInfo lottery = Lottery(old);
        lottery.setGoodsStock(lottery.getGoodsStock() - 1);
        GoodsInfo goodsInfo = goodsInfoRepo.save(lottery);
        userInfoRepo.findById(userId).ifPresent(userInfo->{
            userInfo.setLotteryTimes(userInfo.getLotteryTimes() - 1);
            userInfoRepo.save(userInfo);
        });
        applicationEventPublisher.publishEvent(new LotteryLogEvent(this,userId,goodsInfo.getId()));
    }
    @Override
    @Transactional
    public void reduceStock2(int userId) {
        ArrayList<Integer> old = new ArrayList<>();
        old.add(-1);
        GoodsInfo lottery = Lottery(old);
        redisTemplate.opsForValue().increment("Lottery:goodsStock:" + lottery.getId(), -1);
//        userInfoRepo.findById(userId).ifPresent(userInfo->{
//            userInfo.setLotteryTimes(userInfo.getLotteryTimes() - 1);
//            userInfoRepo.save(userInfo);
//        });
    }

    public GoodsInfo Lottery(List<Integer> old) {
        int i = new Random().nextInt(3);
        if (!old.contains(i)) {

            List<GoodsInfo> goodsInfos = goodsInfoRepo.findAll();
            GoodsInfo goodsInfo = goodsInfos.get(i);
            if (goodsInfo.getGoodsStock() > 0) return goodsInfo;
            else {
                old.add(i);
                Lottery(old);
            }
        } else return null;
        return null;
    }
}
