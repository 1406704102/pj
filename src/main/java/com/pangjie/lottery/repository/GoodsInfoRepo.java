package com.pangjie.lottery.repository;

import com.pangjie.lottery.entiy.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface GoodsInfoRepo extends JpaRepository<GoodsInfo, Integer>, JpaSpecificationExecutor<Integer> {
//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<GoodsInfo> findAllById(Integer id);
}
