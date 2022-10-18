package com.pangjie.doubleDBConfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTransactional {
    /*
     * @Author PangJie___
     * @Description //TODO 当前方法的key
     * @Date 下午5:11 12/10/2022
     * param 
     * return 
     */
    String key();

    /*
     * @Author PangJie___
     * @Description //TODO 需要监视的key
     * @Date 下午5:12 12/10/2022
     * param 
     * return 
     */
    String observedKey();

    /*
     * @Author PangJie___
     * @Description //TODO key存在时间
     * @Date 下午4:33 13/10/2022
     * param 
     * return 
     */
    long expireSeconds() default 10;

}
