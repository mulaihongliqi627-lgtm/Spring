package com.amadeus.config;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

// 用来创建dashscope访问客户端的配置类
@Configuration
public class AIConfig {

    @Bean
    public HttpComponentsClientHttpRequestFactory dashScopeFactory() {
        // 1. 构建请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.of(30, TimeUnit.SECONDS))
                .setResponseTimeout(Timeout.of(120, TimeUnit.SECONDS))
                .setConnectionRequestTimeout(Timeout.of(120, TimeUnit.SECONDS))
                .build();
        // 2. 创建HttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        // 3. 用自定义的httpClient生成请求工厂
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(30000);
        return factory;
    }


    @Bean
    public RestTemplate dashScopeRestTemplate(HttpComponentsClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }


    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("你是一个熟悉Excel的分析助手，能够根据用户输入给出明确的答案")
                .build();
    }
}
