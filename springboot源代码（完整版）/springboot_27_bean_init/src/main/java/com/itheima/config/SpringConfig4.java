package com.itheima.config;

import com.itheima.bean.DogFactoryBean;
import org.springframework.context.annotation.Import;

//@Import({Dog.class,DbConfig.class})
@Import(DogFactoryBean.class)
public class SpringConfig4 {

}
