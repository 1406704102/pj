package com.pangjie.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WxConfig {
    @Bean
    public WxMaConfig wxMaConfig() {
        //获取微信配置
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid("properties.getAppId()");
        config.setSecret("properties.getAppSecret()");
//        config.setToken(properties.getToken());
//        config.setAesKey(properties.getAesKey());
        return config;
    }


    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }

}