package com.itheima.app;

import com.itheima.config.SpringConfig3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App3 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig3.class);
        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println(ctx.getBean("dog"));
        System.out.println(ctx.getBean("dog"));
        System.out.println(ctx.getBean("dog"));
    }
}
