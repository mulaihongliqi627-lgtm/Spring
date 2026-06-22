package com.amadeus.rabbitmqspringboot.controller;


import com.amadeus.rabbitmqspringboot.constant.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/producer")
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("/work")
    public String work(){
        for(int i = 0;i < 10;i++){
            rabbitTemplate.convertAndSend("", Constants.WORK_QUEUE, "hello spring amqp: work..."+i);
        }
        return "ok";
    }

    @RequestMapping("/fanout")
    public String fanoutProduct(@RequestParam(defaultValue = "10") int count){
        for (int i = 0; i < count; i++) {
            rabbitTemplate.convertAndSend(Constants.FANOUT_EXCHANGE, "", "hello spring amqp: fanout..." + i);
        }
        return "ok";
    }

    @RequestMapping("/direct")
    public String directProduct(@RequestParam(defaultValue = "10") String routingKey){
        rabbitTemplate.convertAndSend(Constants.DIRECT_EXCHANGE, routingKey, "hello spring amqp:direct, my routing key is "+routingKey);
        return "发送成功";
    }

    @RequestMapping("/topic/{routingKey}")
    public String topic(@PathVariable("routingKey") String routingKey){
        rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE, routingKey, "hello spring amqp:topic, my routing key is "+routingKey);
        return "发送成功";
    }
}
