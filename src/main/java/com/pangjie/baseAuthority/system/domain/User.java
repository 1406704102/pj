package com.pangjie.baseAuthority.system.domain;


import com.pangjie.baseAuthority.config.annotation.Query;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_user_info")
public class User {

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

    /*
     * @Author pangjie
     * @Description //TODO  referencedColumnName -> 关联表中对应的id        OrderBy -> 排序
     * @Date 17:52 2020/11/18 0018
     * @Param
     * @return
     */
    @ManyToMany
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    @OrderBy("createTime desc")
    private Set<Role> roleInfos;

}
