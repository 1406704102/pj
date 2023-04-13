package com.pangjie.jpa.entity;

import com.pangjie.jpa.config.annotation.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_user_info")
public class UserInfo {

    public static String string = "11";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 1;

    @Query
    @Column(name = "user_name")
    private String userName;

    @Query
    @Column(name = "pass_word")
    private String passWord;

    @Column(name = "create_time")
    private Timestamp createTime;

    /*
     * @Author pangjie
     * @Description //TODO  referencedColumnName -> 关联表中对应的id        OrderBy -> 排序
     *
     *      没有关联表:{
     *      @OneToOne
     *      @JoinColumn(name = "video_id2")
     *      }
     *
     * @Date 17:52 2020/11/18 0018
     * @Param 
     * @return 
     */
//    @OneToOne
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    @OrderBy("createTime desc")
    private Set<RoleInfo> roleInfos;


    @Column(name = "lottery_times", columnDefinition = "0")
    private Integer lotteryTimes;
}
