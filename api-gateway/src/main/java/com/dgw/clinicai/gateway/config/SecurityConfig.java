package com.dgw.clinicai.gateway.config;

import com.dgw.clinicai.gateway.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // Allow all requests to the authentication service
                        .pathMatchers("/api/v1/auth/**").permitAll()
                        // All other requests must be authenticated
                        .anyExchange().authenticated()
                )
                // We can't use .addFilterBefore() in reactive security.
                // Instead, we apply the filter to the routes in the Gateway configuration.
                // This configuration here is primarily for path-based authorization.
                // The actual filtering logic is applied via RouteLocator bean modification.
                // For simplicity, we will apply the filter directly in the route definitions.
                // A more advanced setup would use a custom GatewayFilterFactory.
                .build();
    }
}