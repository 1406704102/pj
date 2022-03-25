package com.pangjie.lottery.sevice;

import com.pangjie.lottery.entiy.LotteryLog;
import org.springframework.stereotype.Service;

@Service
public interface LotteryLogService {
    LotteryLog add(LotteryLog lotteryLog);
}
