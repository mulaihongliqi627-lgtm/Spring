package com.amadeus.rabbitextensiondemo.listener;

import com.amadeus.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PresListener {

    @RabbitListener(queues = Constants.PRES_QUEUE)
    public void handMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.printf("接收到消息 : %s , deliverTag : %d%n",
                    new String(message.getBody(), StandardCharsets.UTF_8),
                    deliveryTag);

            System.out.println("业务逻辑处理");
            System.out.println("业务处理完成");

            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            System.out.println("消息处理失败 :" + e.getMessage());
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
