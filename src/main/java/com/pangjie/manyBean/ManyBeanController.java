package com.pangjie.manyBean;

import com.pangjie.springSecurity.annotation.WithoutToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("many")
@RequiredArgsConstructor
public class ManyBeanController {

//        @Autowired
//        @Qualifier("beanTwo") //根据bean的名字注入

//    private final Many beanTwo; //根据bean的名字注入

//    private final Many many;

//    private final Map<String, Many> many; //key是bean的名字,value是bean

    private final List<Many> manyList;

//    @WithoutToken
//    @GetMapping("/call")
//    public String call() {
//        return many.callBack();
//    }

//    @WithoutToken
//    @GetMapping("/callByName")
//    public String callByName() {
//        return beanTwo.callBack();
//    }

//    @WithoutToken
//    @GetMapping("/callByMap")
//    public HashMap<String, String> call2() {
//        HashMap<String, String> hashMap = new HashMap<>();
//        many.forEach((k, v) -> {
//            System.out.println(k);
//            String callBack = v.callBack();
//            hashMap.put(k, callBack);
//        });
//        return hashMap;
//    }

    @WithoutToken
    @GetMapping("/callByList")
    public List<String> call2() {
        List<String> arrayList = new ArrayList<>();
        manyList.forEach(f -> {
            String callBack = f.callBack();
            arrayList.add(callBack);
        });
        return arrayList;
    }

}
