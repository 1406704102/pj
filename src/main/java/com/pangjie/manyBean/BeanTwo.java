package com.pangjie.manyBean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2) //注入list时排序
public class BeanTwo implements Many{
    @Override
    public String callBack() {
        return "我是beanTwo";
    }
}