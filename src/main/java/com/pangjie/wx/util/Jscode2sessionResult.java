package com.pangjie.wx.util;

import lombok.Data;

@Data
public class Jscode2sessionResult {
    private String openid;
    private String session_key;
    private String expires_in;
}
