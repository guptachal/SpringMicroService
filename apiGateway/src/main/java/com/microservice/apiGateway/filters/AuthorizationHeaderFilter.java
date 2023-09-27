package com.microservice.apiGateway.filters;

import com.microservice.apiGateway.jwtHandler.JwtHandler;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Value("${jwt.secret}") // Your JWT secret key
    private String jwtSecret;
    @Autowired
    private JwtHandler handler;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }
    public static class Config{
        //Put Configurations properties here
    }
    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) ->{
            System.out.println("test");
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer ","");
            System.out.println("test "+ token);
            System.out.println("Subject: "+ handler.getSubject(token));
            System.out.println(handler.isTokenValid(handler.getSubject(token),token));
            if(!handler.isTokenValid(handler.getSubject(token),token)){
                System.out.println("invalid token");
                return onError(exchange, "Authorization Failed!", HttpStatus.UNAUTHORIZED);
            }
            System.out.println("valid token");
            return chain.filter(exchange);
        };
    }
    private Mono<Void> onError(ServerWebExchange exchange, String no_authorization_header, HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        return response.setComplete();
    }

}