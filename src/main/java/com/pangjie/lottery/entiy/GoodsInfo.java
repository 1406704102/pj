package com.pangjie.lottery.entiy;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "goods_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_stock")
    private Integer goodsStock;

    @Transient
    private Integer goodsProbability;
    @Transient
    private Timestamp createTime;
}
