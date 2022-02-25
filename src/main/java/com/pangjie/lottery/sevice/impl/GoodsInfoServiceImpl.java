package com.pangjie.lottery.sevice.impl;

import com.pangjie.PjApplication;
import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.lottery.repository.GoodsInfoRepo;
import com.pangjie.lottery.sevice.GoodsInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private final GoodsInfoRepo goodsInfoRepo;

    @Override
    public List<GoodsInfo> findAll() {
        return goodsInfoRepo.findAll();
    }

    @Override
    @Transactional
    public  void reduceStock(GoodsInfo goodsInfo) {
        System.out.println(Thread.currentThread().getName());
        GoodsInfo goodsInfo1 = goodsInfoRepo.findAllById(1).get();
        System.out.println("当前库存" + goodsInfo1.getGoodsStock());
        goodsInfo1.setGoodsStock(goodsInfo1.getGoodsStock() - 1);
        goodsInfoRepo.save(goodsInfo1);
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------");

    }
}
