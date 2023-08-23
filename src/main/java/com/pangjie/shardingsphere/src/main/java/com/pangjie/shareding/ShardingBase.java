package com.pangjie.shardingsphere.src.main.java.com.pangjie.shareding;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

public class ShardingBase extends Model<ShardingBase> implements Serializable {

    private Long id;

    private String name;

    private Long userId;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}