package com.amadeus.rabbitextensiondemo.listener;

import com.amadeus.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


//@Component
public class TransListener {

    @RabbitListener(queues = Constants.TRANS_QUEUE)
    public void listenerQueue(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        System.out.printf("接收到消息: %s, deliveryTag: %d%n",
                new String(message.getBody(), "UTF-8"),
                deliveryTag);

        // 手动确认
        channel.basicAck(deliveryTag, false);
    }
}
