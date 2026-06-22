package com.amadeus.rabbitextensiondemo.listener;

import com.amadeus.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DelayListener {
    @RabbitListener(queues = Constants.DELAY_QUEUE)
    public void delayHandMessage(Message message, Channel channel) throws Exception {

        long deliverTag = message.getMessageProperties().getDeliveryTag();
        try{
            //消费者逻辑
            System.out.printf("[delay.queue] %tc 接收到消息: %s \n",
                    new Date(),
                    new String(message.getBody(),"UTF-8"));
        }catch (Exception e){
            System.out.println("消费失败");
            channel.basicNack(deliverTag,false,true);
        }
    }
}
