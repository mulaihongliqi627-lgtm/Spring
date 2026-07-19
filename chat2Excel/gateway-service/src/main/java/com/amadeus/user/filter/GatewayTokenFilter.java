package com.amadeus.user.filter;

import java.nio.charset.StandardCharsets;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Validates the gateway token carried by incoming requests.
 */
@Component
@Slf4j
public class GatewayTokenFilter implements GlobalFilter, Ordered {

    private static final String GATEWAY_TOKEN_HEADER = "X-Gateway-Token";

    /**
     * 网关令牌
     */
    @Value("${security.gateway.token:}")
    private String gatewayToken;

    /**
     * 网关令牌是否启用(网关控制器)
     */
    @Value("${security.gateway.enabled:true}")
    private boolean gatewayCheckEnable;

    @PostConstruct
    void logStatus() {
        if (gatewayCheckEnable) {
            log.info("网关令牌过滤器已经启动");
        } else {
            log.info("允许直接访问后端");
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!gatewayCheckEnable) {
            return chain.filter(exchange);
        }

        /**
         * 获取token
         */
        String requestToken = exchange.getRequest().getHeaders().getFirst(GATEWAY_TOKEN_HEADER);
        if (!StringUtils.hasText(requestToken)) {
            log.warn("网关令牌为空: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI().getPath());
            return unauthorized(exchange.getResponse(), "禁止访问");
        }

        if (!requestToken.equals(gatewayToken)) {
            log.warn("网关令牌错误: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI().getPath());
            return unauthorized(exchange.getResponse(), "禁止访问");
        }

        log.info("网关令牌验证通过: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI().getPath());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"code\":%d,\"message\":\"%s\"}", HttpStatus.FORBIDDEN.value(), message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))));
    }
}
