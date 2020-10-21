package com.pangjie.wx.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenResult implements Serializable {

    private String access_token;
    private Integer expires_in;
    private String errcode;
    private String errmsg;
    private String openid;
}