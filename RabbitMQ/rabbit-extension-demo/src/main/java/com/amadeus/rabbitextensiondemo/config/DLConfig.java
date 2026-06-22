package com.amadeus.rabbitextensiondemo.config;


import com.amadeus.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DLConfig {
    //正常的交换机和队列
    //演示TTL+死信队列模拟的延迟队列存在问题, 先把这段代码注释掉
//    @Bean("normalQueue")
//    public Queue normalQueue(){
//        return QueueBuilder.durable(Constants.NORMAL_QUEUE)
//                .deadLetterExchange(Constants.DL_EXCHANGE)
//                .deadLetterRoutingKey("dlx")
//                .ttl(10000)
//                .maxLength(10L)
//                .build();
//    }
    @Bean("normalQueue")
    public Queue normalQueue(){
        return QueueBuilder.durable(Constants.NORMAL_QUEUE)
                .deadLetterExchange(Constants.DL_EXCHANGE)
                .deadLetterRoutingKey("dlx")
                .build();
    }
    @Bean("normalExchange")
    public DirectExchange normalExchange(){
        return ExchangeBuilder.directExchange(Constants.NORMAL_EXCHANGE).build();
    }

    @Bean("normalBinding")
    public Binding normalBinding(@Qualifier("normalQueue") Queue queue, @Qualifier("normalExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("normal").noargs();
    }
    //死信交换机和队列
    @Bean("dlQueue")
    public Queue dlQueue(){
        return QueueBuilder.durable(Constants.DL_QUEUE).build();
    }
    @Bean("dlExchange")
    public DirectExchange dlExchange(){
        return ExchangeBuilder.directExchange(Constants.DL_EXCHANGE).build();
    }

    @Bean("dlBinding")
    public Binding dlBinding(@Qualifier("dlQueue") Queue queue, @Qualifier("dlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("dlx").noargs();
    }
}
