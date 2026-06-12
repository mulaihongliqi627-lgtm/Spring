package com.amadeus.rabbitmqspringboot.listener;

import com.amadeus.rabbitmqspringboot.constant.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;


@Component
public class WorkListener {

    @RabbitListener(queues = Constants.WORK_QUEUE)
    public void work(Message message, Channel channel){
        System.out.println("listener 1 ["+Constants.WORK_QUEUE+"] 接收到消息:" +message + ",channel:"+channel);
    }

    @RabbitListener(queues = Constants.WORK_QUEUE)
    public void queueListener2(String message){
        System.out.println("listener 2 ["+Constants.WORK_QUEUE+"] 接收到消息:" +message);
    }



}
