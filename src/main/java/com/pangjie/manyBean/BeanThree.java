package com.pangjie.manyBean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1) //注入list时排序
public class BeanThree implements Many{
    @Override
    public String callBack() {
        return "我是beanThree";
    }
}