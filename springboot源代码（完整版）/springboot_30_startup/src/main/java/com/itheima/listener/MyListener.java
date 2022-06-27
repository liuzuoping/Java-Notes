package com.itheima.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

//public class MyListener implements ApplicationListener<ApplicationStartingEvent> {
//
//    @Override
//    public void onApplicationEvent(ApplicationStartingEvent event) {
//        System.out.println("=================================");
//        System.out.println(event.getTimestamp());
//        System.out.println(event.getSource());
//        System.out.println(event.getClass());
//    }
//}
public class MyListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("=============================");
    }
}
