package com.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/1/22 16:24
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class Payment8001Applictaion {
    public static void main(String[] args) {
        SpringApplication.run(Payment8001Applictaion.class, args);
    }

}
