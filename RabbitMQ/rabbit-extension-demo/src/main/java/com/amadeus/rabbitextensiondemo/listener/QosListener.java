package com.amadeus.rabbitextensiondemo.listener;


import com.amadeus.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QosListener {

    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void listenerQueue(Message message ,Channel channel) throws Exception {

        try{
            System.out.printf("[qos.queue] %s 111接收到消息: %s\n",
                    new Date(),
                    new String(message.getBody(), "UTF-8"));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //multiple : false , requeue : false
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }

    }

    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void ListenQueue2(Message message,Channel channel) throws  Exception{
        try{
            System.out.printf("[qos.queue] %s 222接收到消息: %s\n",
                    new Date(),
                    new String(message.getBody(), "UTF-8"));

//            Thread.sleep(100);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //multiple : false , requeue : false
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }
    }
}
