package com.pangjie.shardingsphere.src.main.java.com.pangjie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.sharding")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}