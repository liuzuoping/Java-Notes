package com.itheima.service.impl.rabbitmq.direct;

import com.itheima.service.MessageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class MessageServiceRabbitmqDirectImpl implements MessageService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列（rabbitmq direct），id："+id);
        amqpTemplate.convertAndSend("directExchange","direct",id);
    }

    @Override
    public String doMessage() {
        return null;
    }
}
