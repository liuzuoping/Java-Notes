package com.itheima.config;

import com.itheima.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = true)
public class SpringConfig33 {
    @Bean
    public Cat cat(){
        return new Cat();
    }
}
