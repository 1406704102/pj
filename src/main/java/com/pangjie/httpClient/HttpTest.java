package com.pangjie.httpClient;

import java.util.HashMap;

public class HttpTest {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            String post = HttpConnectionPoolUtil.post("http://127.0.0.1:6770/order", new HashMap<>(), new HashMap<>());
        }
        System.out.println(System.currentTimeMillis() - start);

//        start = System.currentTimeMillis();
//        for (int i = 0; i < 500; i++) {
//            h
//            String post = HttpConnectionPoolUtil.post("http://127.0.0.1:6770/order", new HashMap<>(), new HashMap<>());
//        }
//        System.out.println(System.currentTimeMillis() - start);
    }
}
