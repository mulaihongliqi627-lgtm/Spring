package com.amadeus.rabbitextensiondemo.listener;

import com.amadeus.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class AckListener {

    //自动确认auto
//    @RabbitListener(queues = Constants.ACK_QUEUE)
//    public void handMessage(Message message) {
//        try {
//            System.out.printf("接收到消息: %s\n", new String(message.getBody(), "UTF-8"));
//
//            System.out.println("业务逻辑处理");
//            int num = 3/0;
//            System.out.println("业务处理完成");
//        } catch (Exception e) {
//            System.err.println("消息处理失败: " + e.getMessage());
//        }
//    }

    //手动确认
    @RabbitListener(queues = Constants.ACK_QUEUE)
    public void handMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //消费者逻辑
            System.out.printf("接收到消息: %s, deliveryTag: %d \n",
                    new String(message.getBody(),
                            "UTF-8"),
                    message.getMessageProperties().getDeliveryTag());

            //进行业务逻辑处理
            System.out.println("业务逻辑处理");
//            int num = 3/0;
            System.out.println("业务处理完成");
            //肯定确认
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
