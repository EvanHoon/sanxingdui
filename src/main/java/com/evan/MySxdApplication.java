package com.evan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.evan.dao")
//@EnableCaching
public class MySxdApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySxdApplication.class, args);
    }
}
