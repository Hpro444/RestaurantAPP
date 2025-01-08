package com.example.notification.NotificationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/notifications/**").permitAll() // Adjust paths as necessary
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .httpBasic(); // or formLogin(), jwt(), etc., depending on your needs

        return http.build();
    }
}
