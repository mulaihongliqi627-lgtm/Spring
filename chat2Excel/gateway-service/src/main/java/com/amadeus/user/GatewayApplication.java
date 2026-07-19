package com.amadeus.user;


import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * 限制网关服务启动时只扫描自己的配置和过滤器
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.amadeus.user.config",
                "com.amadeus.user.filter"
        },
        exclude = {
                DataSourceAutoConfiguration.class,
                MybatisPlusAutoConfiguration.class
        }
)
@Slf4j
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("网关服务启动中 port: 8848");
    }
}
