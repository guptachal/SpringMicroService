package com.microservice.apiGateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PreFilter implements GlobalFilter {
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Pre filter!");
        logger.info("Pre filter is executed!");
        String requestPath = exchange.getRequest().getPath().toString();
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return chain.filter(exchange);
    }
}
