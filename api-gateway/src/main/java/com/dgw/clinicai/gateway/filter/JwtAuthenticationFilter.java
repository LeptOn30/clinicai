package com.dgw.clinicai.gateway.filter;

import com.dgw.clinicai.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isAuthMissing(request)) {
            return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
        }

        final String token = getAuthHeader(request);

        try {
            if (jwtUtil.isTokenInvalid(token)) {
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization") ||
                !request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ");
    }
}