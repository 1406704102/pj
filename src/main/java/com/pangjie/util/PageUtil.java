package com.pangjie.util;

import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageUtil {
    public static Map<String,Object> toPage(Page page) {
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",page.getContent());
        map.put("totalElements",page.getTotalElements());
        return map;
    }
}
