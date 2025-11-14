package com.dgw.clinicai.gateway.filter;

import com.dgw.clinicai.gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthorizationFilterFactory extends AbstractGatewayFilterFactory<AuthorizationFilterFactory.Config> {

    private final JwtUtil jwtUtil;

    public AuthorizationFilterFactory(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            final String token = getAuthHeader(request);

            try {
                if (jwtUtil.isTokenInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }

                if (!hasRequiredRole(token, config.getRequiredRoles())) {
                    return this.onError(exchange, "User does not have required role", HttpStatus.FORBIDDEN);
                }

            } catch (Exception e) {
                log.error("Error during authorization: {}", e.getMessage());
                return this.onError(exchange, "Authorization error", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private boolean hasRequiredRole(String token, List<String> requiredRoles) {
        List<String> userRoles = jwtUtil.getRolesFromToken(token);
        return userRoles.stream().anyMatch(requiredRoles::contains);
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

    public static class Config {
        private String requiredRoles;

        public List<String> getRequiredRoles() {
            return Arrays.asList(requiredRoles.split(","));
        }

        public void setRequiredRoles(String requiredRoles) {
            this.requiredRoles = requiredRoles;
        }
    }
}