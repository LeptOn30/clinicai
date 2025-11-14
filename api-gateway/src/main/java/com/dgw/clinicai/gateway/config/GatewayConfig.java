package com.dgw.clinicai.gateway.config;

import com.dgw.clinicai.gateway.filter.AuthorizationFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final RouteLocator routes;

    public GatewayConfig(AuthorizationFilterFactory authFilterFactory, RouteLocator routes) {
        this.authFilterFactory = authFilterFactory;
        this.routes = routes;
    }

    private final AuthorizationFilterFactory authFilterFactory;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("identity-service", r -> r.path("/api/v1/auth/**")
                        .uri("http://localhost:8081"))

                .route("patient-service", r -> r.path("/api/v1/patients/**")
                        .filters(f -> f.filter(authFilterFactory.apply(c -> c.setRequiredRoles("ADMIN"))))
                        .uri("http://localhost:8082"))

                .route("scheduling-service", r -> r.path("/api/v1/appointments/**")
                        .filters(f -> f.filter(authFilterFactory.apply(c -> c.setRequiredRoles("ADMIN,STAFF"))))
                        .uri("http://localhost:8084"))

                .route("billing-service", r -> r.path("/api/v1/invoices/**")
                        .filters(f -> f.filter(authFilterFactory.apply(c -> c.setRequiredRoles("STAFF"))))
                        .uri("http://localhost:8083"))

                .build();
    }
}