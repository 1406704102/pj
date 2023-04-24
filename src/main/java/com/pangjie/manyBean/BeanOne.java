package com.pangjie.manyBean;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(3) //注入list时排序
//@Primary //多个bean默认注入这个bean
public class BeanOne implements Many{
    @Override
    public String callBack() {
        return "我是beanOne";
    }
}
