package com.pangjie.lottery.repository;

import com.pangjie.lottery.entiy.LotteryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LotteryLogRepo extends JpaRepository<LotteryLog, Integer>, JpaSpecificationExecutor<Integer> {
}
