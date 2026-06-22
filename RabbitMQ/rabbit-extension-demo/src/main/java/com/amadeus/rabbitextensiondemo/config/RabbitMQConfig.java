package com.amadeus.rabbitextensiondemo.config;



import com.amadeus.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    //消息确认
    @Bean("ackQueue")
    public Queue RabbitMQConfig(){
        return QueueBuilder.durable(Constants.ACK_QUEUE).build();
    }
    @Bean("directExchange")
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(Constants.ACK_EXCHANGE).build();
    }
    @Bean("ackBinding")
    public Binding ackBinding(@Qualifier("directExchange") DirectExchange directExchange, @Qualifier("ackQueue") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("ack");
    }

    //持久化
    @Bean("presQueue")
    public Queue presQueue(){
        return QueueBuilder
                .durable(Constants.PRES_QUEUE)
                .build();
    }

    @Bean("presExchange")
    public DirectExchange presExchange(){
        return ExchangeBuilder.directExchange(Constants.PRES_EXCHANGE)
                .durable(false)
                .build();
    }
    @Bean("presBinding")
    public Binding presBinding(@Qualifier("presQueue") Queue queue, @Qualifier("presExchange") Exchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("pres")
                .noargs();
    }

    //发送方确认
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(Constants.CONFIRM_QUEUE).build();
    }

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(Constants.CONFIRM_EXCHANGE).build();
    }

    @Bean("confirmBinding")
    public Binding confirmBinding(@Qualifier("confirmQueue") Queue queue, @Qualifier("confirmExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("confirm").noargs();
    }

    //ttl
    //未设置ttl
    @Bean("ttlQueue")
    public Queue ttlQueue(){
        return QueueBuilder.durable(Constants.TTL_QUEUE).build();
    }
    //设置ttl
    @Bean("ttlQueue2")
    public Queue ttlQueue2(){
        return QueueBuilder.durable(Constants.TTL_QUEUE2).ttl(20000).build();  //设置队列的ttl为20s
    }

    @Bean("ttlQueue3")
    public Queue ttlQueue3(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 20000);
        return QueueBuilder.durable(Constants.TTL_QUEUE2).withArguments(map).build();  //设置队列的ttl为20s
    }

    @Bean("ttlExchange")
    public DirectExchange ttlExchange(){
        return ExchangeBuilder.directExchange(Constants.TTL_EXCHANGE).build();
    }
    @Bean("ttlBinding")
    public Binding ttlBinding(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
    }
    @Bean("ttlBinding2")
    public Binding ttlBinding2(@Qualifier("ttlQueue2") Queue queue, @Qualifier("ttlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
    }

    //事务
    @Bean("transQueue")
    public Queue transQueue() {
        return QueueBuilder.durable(Constants.TRANS_QUEUE).build();
    }




}
