package com.learn.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/1/25 16:51
 */
@Configuration
public class MySelfRule {

    /**
     * 定义为随机
     * @return
     */
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
