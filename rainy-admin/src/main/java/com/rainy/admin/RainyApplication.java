package com.rainy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * spring-boot 启动类
 *
 * @author renguangli
 * @date 2022/3/17 15:08
 */
@EnableCaching
@SpringBootApplication
public class RainyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainyApplication.class, args);
    }

}
