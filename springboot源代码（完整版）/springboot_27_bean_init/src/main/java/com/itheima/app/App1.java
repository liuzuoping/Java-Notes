package com.itheima.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App1 {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationCOntext1.xml");
//        Object cat = ctx.getBean("cat");
//        System.out.println(cat);
//        Dog dog = ctx.getBean(Dog.class);
//        System.out.println(dog);
        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
