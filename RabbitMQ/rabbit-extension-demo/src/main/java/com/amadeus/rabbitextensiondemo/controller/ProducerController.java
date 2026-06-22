package com.amadeus.rabbitextensiondemo.controller;


import com.amadeus.rabbitextensiondemo.constant.Constants;
import jakarta.annotation.Resource;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitTemplate confirmRabbitTemplate;
    @Autowired
    @Qualifier("transRabbitTemplate")
    private RabbitTemplate transRabbitTemplate;

    @RequestMapping("/ack")
    public String ack(){
        rabbitTemplate.convertAndSend(Constants.ACK_EXCHANGE,"ack","hello cheems");
        System.out.println("发送成功");
        return "success";
    }

    @RequestMapping("/pres")
    public String pres() {
        Message message = new Message("Presistent test...".getBytes(), new MessageProperties());
        //消息非持久化
        //message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        //消息持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        System.out.println(message);
        rabbitTemplate.convertAndSend(Constants.PRES_EXCHANGE, "pres", message);
        return "消息发送成功";
    }

    @RequestMapping("/confirm")
    public String confirm(){
        // 1. 创建关联数据对象，ID为"1"
        CorrelationData correlationData = new CorrelationData("1");
        // 2. 使用带确认回调的 RabbitTemplate 发送消息
        confirmRabbitTemplate.convertAndSend(Constants.CONFIRM_EXCHANGE, "confirm111", "hello confirm", correlationData);
        return "消息发送成功";
    }

    @RequestMapping("/retry")
    public String retry(){
        System.out.println("retry...");
        rabbitTemplate.convertAndSend(Constants.RETRY_EXCHANGE, "retry", "retry test...");
        return "消息发送成功";
    }

    @RequestMapping("/ttl1")
    public String ttl1(){
        System.out.println("ttl1 test...");
        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE, "ttl", "ttl test 10s...", message -> {
            message.getMessageProperties().setExpiration("10000");//过期时间为10s
            return message;
        });

        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE, "ttl", "ttl test 30s...", message -> {
            message.getMessageProperties().setExpiration("30000");//过期时间为30s
            return message;
        });
        return "消息发送成功";
    }

    @RequestMapping("/ttl2")
    public String ttl2() {
        System.out.println("ttl2...");
        //发送普通消息
        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE, "ttl", "ttl test...");
        return "消息发送成功";
    }

    @RequestMapping("/dl")
    public String dl(){
        System.out.println("dl...");
        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE,"normal", "dl test...",message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
        System.out.printf("%s 消息发送成功 \n",new Date());
        return "消息发送成功";
    }

    @RequestMapping("delay")
    public String delay(){
        System.out.println("delay...");
        rabbitTemplate.convertAndSend(Constants.DELAY_EXCHANGE, "delay", "delay test 10s...", message -> {
            message.getMessageProperties().setDelayLong(10000L);
            return message;
        });
        return "消息发送成功";
    }


    @Transactional(transactionManager = "rabbitTransactionManager")
    @RequestMapping("/trans")
    public String trans(){
        System.out.println("trans test...");
        transRabbitTemplate.convertAndSend("",Constants.TRANS_QUEUE, "trans test 1...");
        int num = 5/0;
        transRabbitTemplate.convertAndSend("",Constants.TRANS_QUEUE, "trans test 2...");
        return "消息发送成功";
    }

    @RequestMapping("/qos")
    public String qos(){
        System.out.println("qos test...");
        for(int i  = 0;i < 20;i++){
            rabbitTemplate.convertAndSend(Constants.QOS_EXCHANGE, "qos", "qos test..." + i);
        }
        return "消息发送成功";
    }
}
