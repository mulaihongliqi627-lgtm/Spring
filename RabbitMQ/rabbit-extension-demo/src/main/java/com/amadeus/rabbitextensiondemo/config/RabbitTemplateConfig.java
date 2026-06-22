package com.amadeus.rabbitextensiondemo.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
    @Bean
    public RabbitTemplate confirmRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置回调方法
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("执行了confirm方法");
                if (ack){
                    System.out.printf("接收到消息, 消息ID: %s \n", correlationData==null? null: correlationData.getId());
                }else {
                    System.out.printf("未接收到消息, 消息ID: %s, cause: %s \n", correlationData==null? null: correlationData.getId(), cause);
                    //相应的业务处理
                }
            }
        });
        //消息被退回时, 回调方法
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("消息退回:"+returned);
            }
        });
        return rabbitTemplate;
    }

    @Bean("transRabbitTemplate")
    public RabbitTemplate transRabbitTemplate(TxConnectionFactoryHolder txConnectionFactoryHolder){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(txConnectionFactoryHolder.getConnectionFactory());
        rabbitTemplate.setChannelTransacted(true);  //开启事务
        return rabbitTemplate;
    }
    @Bean("rabbitTransactionManager")
    public RabbitTransactionManager rabbitTransactionManager(TxConnectionFactoryHolder txConnectionFactoryHolder){
        return new RabbitTransactionManager(txConnectionFactoryHolder.getConnectionFactory());
    }

    @Bean(destroyMethod = "destroy")
    public TxConnectionFactoryHolder txConnectionFactoryHolder(@Value("${spring.rabbitmq.addresses}") String addresses) throws Exception {
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setUri(addresses);
        return new TxConnectionFactoryHolder(new CachingConnectionFactory(rabbitConnectionFactory));
    }

    public static class TxConnectionFactoryHolder {
        private final CachingConnectionFactory connectionFactory;

        public TxConnectionFactoryHolder(CachingConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
        }

        public ConnectionFactory getConnectionFactory() {
            return connectionFactory;
        }

        public void destroy() {
            connectionFactory.destroy();
        }
    }

}
