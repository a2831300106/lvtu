package com.lvtu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lvtu.mapper")
public class TravelServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelServerApplication.class, args);
    }
}
