package org.microservices.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import feign.RequestInterceptor;

@Configuration
public class FeignSecurityConfiguration {
    @Bean
    public RequestInterceptor jwtRelayRequestInterceptor() {
        return requestTemplate -> {
            Authentication authenticaiton = SecurityContextHolder.getContext().getAuthentication();
            if (authenticaiton instanceof JwtAuthenticationToken jwtAuth) {
                String tokenValue = jwtAuth.getToken().getTokenValue();
                requestTemplate.header(
                    HttpHeaders.AUTHORIZATION,
                    "Bearer " + tokenValue
                );
            }
        };
    }
}
