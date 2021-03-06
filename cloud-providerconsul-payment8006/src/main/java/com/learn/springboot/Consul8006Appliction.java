package com.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/2/4 14:07
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Consul8006Appliction {
    public static void main(String[] args) {
        SpringApplication.run(Consul8006Appliction.class, args);
    }
}
