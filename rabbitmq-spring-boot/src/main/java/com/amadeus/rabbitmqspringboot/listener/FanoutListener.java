package com.amadeus.rabbitmqspringboot.listener;

import com.amadeus.rabbitmqspringboot.constant.Constants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutListener {
    //指定监听队列的名称
    @RabbitListener(queues = Constants.FANOUT_QUEUE1)
    public void ListenerQueue(String message){
        System.out.println("["+Constants.FANOUT_QUEUE1+ "]接收到消息:"+ message);
    }
    @RabbitListener(queues = Constants.FANOUT_QUEUE2)
    public void ListenerQueue2(String message){
        System.out.println("["+Constants.FANOUT_QUEUE2+ "]接收到消息:"+ message);
    }
}
