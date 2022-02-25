package com.pangjie.lottery.sevice;

import com.pangjie.lottery.entiy.GoodsInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsInfoService {
    List<GoodsInfo> findAll();

    void reduceStock(GoodsInfo goodsInfo);
}
