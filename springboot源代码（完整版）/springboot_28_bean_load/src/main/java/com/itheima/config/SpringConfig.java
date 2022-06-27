package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//@Import(MyImportSelector.class)
//@Import(Mouse.class)
@ComponentScan("com.itheima.bean")
public class SpringConfig {

    /*
    @Bean
//    @ConditionalOnClass(name = "com.itheima.bean.Wolf")
//    @ConditionalOnMissingClass("com.itheima.bean.Mouse")
    @ConditionalOnBean(name="jerry")
//    @ConditionalOnMissingClass("com.itheima.bean.Dog")
//    @ConditionalOnNotWebApplication
    @ConditionalOnWebApplication
    public Cat tom(){
        return new Cat();
    }
*/

    @Bean
    @ConditionalOnClass(name="com.mysql.jdbc.Driver")
    public DruidDataSource dataSource(){
        return new DruidDataSource();
    }

}
