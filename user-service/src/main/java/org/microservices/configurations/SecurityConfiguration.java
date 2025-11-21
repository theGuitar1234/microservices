package org.microservices.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/db-console-user/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("SCOPE_read:invoices", "ROLE_USER")
                .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("SCOPE_write:invoices")
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
