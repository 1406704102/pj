package com.pangjie.jpa.entity;

import com.pangjie.jpa.config.annotation.Query;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Query
    @Column(name = "name")
    private String name;

    @Column(name = "openid")
    private String openid;

    @Column(name = "community_id")
    private Integer communityId;

    @Column(name = "community_name")
    private String communityName;

    @Column(name = "is_lottery")
    private Integer isLottery;

    @Column(name = "is_light")
    private Integer isLight;

    @Column(name = "create_time")
    private Timestamp createTime;

}
