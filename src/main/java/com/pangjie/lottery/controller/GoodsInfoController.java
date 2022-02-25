package com.pangjie.lottery.controller;


import com.pangjie.lottery.entiy.GoodsInfo;
import com.pangjie.lottery.sevice.GoodsInfoService;
import com.pangjie.springSecurity.annotation.WithoutToken;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/goodsInfo")
@AllArgsConstructor
public class GoodsInfoController {

    private final GoodsInfoService goodsInfoService;

    @PostMapping("/doLottery")
    @WithoutToken
    public ResponseEntity<Object> doLottery() {
        List<GoodsInfo> goodsInfos = goodsInfoService.findAll();
        int i = new Random().nextInt(3);
//        System.out.println("抽中了:" + goodsInfos.get(i).getGoodsName());
        synchronized (this) {
            goodsInfoService.reduceStock(goodsInfos.get(0));
        }
        return new ResponseEntity<>(goodsInfos.get(i), HttpStatus.OK);
    }
}
