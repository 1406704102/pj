package com.pangjie.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 库存扣减脚本(多个脚本)
     */
    @Bean
    public Map<String,DefaultRedisScript<Boolean>> script() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redis/test.lua")));
        redisScript.setResultType(Boolean.class);
        HashMap<String,DefaultRedisScript<Boolean>> hashMap = new HashMap<>();
        hashMap.put("lua1", redisScript);
        DefaultRedisScript<Boolean> redisScript2 = new DefaultRedisScript<>();
        redisScript2.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redis/test2.lua")));
        redisScript2.setResultType(Boolean.class);
        hashMap.put("lua2", redisScript2);
        return hashMap;
    }


}
