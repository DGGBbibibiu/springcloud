package com.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/1/25 15:14
 */

@SpringBootApplication
@EnableEurekaServer
public class Eureka7001Application {
    public static void main(String[] args) {
        SpringApplication.run(Eureka7001Application.class, args);
    }
}
