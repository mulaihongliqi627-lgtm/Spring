package com.amadeus.rabbitextensiondemo.config;

import com.amadeus.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DelayConfig {
    @Bean("delayQueue")
    public Queue dealayQueue(){
        return QueueBuilder
                .durable(Constants.DELAY_QUEUE)
                .build();
    }

    @Bean("delayExchange")
    public Exchange delayExchange(){
        return ExchangeBuilder
                .directExchange(Constants.DELAY_EXCHANGE)
                .durable(true)
                .delayed()
                .build();
    }

    @Bean("delayBlinding")
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") Exchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("delay")
                .noargs();
    }
}
