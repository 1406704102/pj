package com.pangjie.doubleDBConfig.aspect;

import com.pangjie.doubleDBConfig.annotation.MyTransactional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class MyTransactionalAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.pangjie.doubleDBConfig.annotation.MyTransactional)")
    public void pointcut() {
    }

    @AfterReturning("pointcut()")
    public void AfterReturning(JoinPoint joinPoint) throws InterruptedException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        MyTransactional annotation = method.getAnnotation(MyTransactional.class);
        String key = annotation.key();
        String[] observedKeys = annotation.observedKey().split(",");

//        int i = 1 / 0;
        // 获取token以及方法标记，生成redisKey和redisValue
        String token = request.getHeader("Authorization");
        String redisKey = "MyTransactional:"
                .concat(key)
                .concat(token);
        long l = annotation.expireSeconds();
        redisTemplate.opsForValue()
                .set(redisKey, token, l, TimeUnit.SECONDS);

        boolean temp = false;
        for (long i = 0; i < l; i++) {
            for (int n = 0; n < observedKeys.length; n++) {
                String observedKey = "MyTransactional:"
                        .concat(observedKeys[n])
                        .concat(token);
                System.out.println("正在寻找" + observedKey);
                if (redisTemplate.opsForValue().get(observedKey) != null) {
                    System.out.println(redisTemplate.opsForValue().get(observedKey));
                    temp = true;
                } else {
                    temp = false;
                }
            }
            if (temp) {
                System.out.println("找到所有key");
                break;
            }
            Thread.sleep(1000);
        }
        if (!temp) {
            throw new RuntimeException("未寻找到对的事物key");
        }
    }
}
