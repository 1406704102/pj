package com.pangjie.lottery.entiy;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "goods_info")
@Data
public class GoodsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_stock")
    private Integer goodsStock;
}
