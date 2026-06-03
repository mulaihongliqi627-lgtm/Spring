package com.amadeus.lotterysystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.amadeus.lotterysystem.dao.mapper")
public class LotterySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotterySystemApplication.class, args);
    }

}
