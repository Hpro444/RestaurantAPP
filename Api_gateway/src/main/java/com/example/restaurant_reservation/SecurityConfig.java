package com.example.restaurant_reservation;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow all requests
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Enable CORS
//
//        return http.build();
//    }
//
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://localhost:5178"); // Frontend origin
//        config.addAllowedHeader("*"); // Allow all headers
//        config.addAllowedMethod("*"); // Allow all HTTP methods
//        config.setAllowCredentials(true); // Allow cookies (if needed)
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config); // Apply to all routes
//        return source;
//    }
//}
