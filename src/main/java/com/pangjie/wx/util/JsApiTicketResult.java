package com.pangjie.wx.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsApiTicketResult implements Serializable {

    private String ticket;
    private Integer expires_in;
    private String errcode;
    private String errmsg;
}