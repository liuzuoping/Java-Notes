package com.itheima.service.impl.rabbitmq.direct.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class MessageListener2 {

    @RabbitListener(queues = "direct_queue")
    public void receive(String id){
        System.out.println("已完成短信发送业务(rabbitmq direct two)，id："+id);
    }

}
