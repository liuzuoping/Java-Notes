package com.itheima.service.impl.rocketmq;

import com.itheima.service.MessageService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class MessageServiceRocketmqImpl implements MessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列（rocketmq），id："+id);
//        rocketMQTemplate.convertAndSend("order_id",id);
        SendCallback callback = new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("消息发送失败！！！！！");
            }
        };
        rocketMQTemplate.asyncSend("order_id",id,callback);
    }

    @Override
    public String doMessage() {
        return null;
    }
}
