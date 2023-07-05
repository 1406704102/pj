package com.pangjie.httpSignCheck;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pangjie.rsa.Sign;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Aspect
@Component
public class HttpSignCheckAspect {

    @Pointcut("@annotation(com.pangjie.httpSignCheck.HttpSignCheck)")
    public void httpCheck() {
    }

    @Around("httpCheck()")
    public Object before(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String sign = request.getHeader("sign");
        //解密sign
        try {
            String s = Sign.decryptByPrivateKey(sign);
            System.out.println(s);
            JSONObject objects = JSONUtil.parseObj(s);
            //比较时间
            long dateLong = objects.get("timestamp", Long.class);
            long between = DateUtil.between(DateUtil.date(dateLong), new Date(), DateUnit.SECOND);

            //获取类名
//            String className = joinPoint.getTarget().getClass().getName();
            //获取方法名
//            String methodName = joinPoint.getSignature().getName();
            //获取参数名
            MethodSignature memberSignature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = memberSignature.getParameterNames();
            //获取参数值
            Object[] args = joinPoint.getArgs();
            JSONObject argsJson = new JSONObject();
            for (int i = 0; i < parameterNames.length; i++) {
                argsJson.putOnce(parameterNames[i], args[i]);
            }
            //比较参数列表是否相同
            boolean equals = StringUtils.equals(s, argsJson.toString());
            //请求时间相差5秒内且参数列表相同
            if (between <= 5000 && equals) {
               return joinPoint.proceed();
            } else {
                return new ResponseEntity<>("请求不合法", HttpStatus.BAD_REQUEST);
            }
        } catch (Throwable e) {
            throw new RuntimeException("解密失败");
        }

    }
}
