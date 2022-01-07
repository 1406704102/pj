package com.pangjie.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "sys_menu_info")
public class MenuInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "level_num")
    private Integer levelNum;

    @Column(name = "title")
    private String title;

    @Column(name = "create_time")
    private Timestamp createTime;


}
