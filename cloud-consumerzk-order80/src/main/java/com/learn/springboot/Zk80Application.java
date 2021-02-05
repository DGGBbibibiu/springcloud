package com.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/2/3 15:10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Zk80Application {
    public static void main(String[] args) {
        SpringApplication.run(Zk80Application.class, args);
    }
}
