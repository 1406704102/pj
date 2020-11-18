package com.pangjie.jpa.entity;

import com.pangjie.jpa.config.annotation.Query;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "sys_user_info")
public class UserInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Query
    @Column(name = "user_name")
    private String userName;

    @Query
    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Timestamp createTime;

}
