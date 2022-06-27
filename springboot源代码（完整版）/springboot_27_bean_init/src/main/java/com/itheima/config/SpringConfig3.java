package com.itheima.config;

import com.itheima.bean.DogFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.itheima.bean","com.itheima.config"})
public class SpringConfig3 {

    @Bean
    public DogFactoryBean dog(){
        return new DogFactoryBean();
    }

}
