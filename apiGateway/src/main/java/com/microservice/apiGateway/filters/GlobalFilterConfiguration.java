package com.microservice.apiGateway.filters;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {

    @Bean
    public GlobalFilter preFilter(){
        return ((exchange, chain) -> {
            System.out.println("Pre filter 2");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                System.out.println("Post filter 2");
            }));
        });
    }
}