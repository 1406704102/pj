package com.pangjie.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/*
 * @Author PangJie___
 * @Description  lua脚本
 * @Date 14:11 2023/4/13
 * param 
 * return 
 */
@Configuration
public class LuaScript {

    /**
     * 库存扣减脚本
     */
    @Bean
    public DefaultRedisScript<Boolean> luaScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redis/test.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

}
