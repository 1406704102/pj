package com.pangjie.lottery.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lottery_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
