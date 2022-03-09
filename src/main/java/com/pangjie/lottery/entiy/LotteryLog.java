package com.pangjie.lottery.entiy;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lottery_log")
@Data
public class LotteryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "goods_id")
    private Integer goodsId;
}
