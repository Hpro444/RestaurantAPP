package com.example.restaurant_reservation.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue notificationQueue() {
        return new Queue("notification-queue", true);
    }

    //    @Bean
//    public SimpleMessageConverter messageConverter() {
//        SimpleMessageConverter converter = new SimpleMessageConverter();
//        converter.setAllowedListPatterns(List.of("com.example.notification.NotificationService.dto.*","java.util.*"));
//        return converter;
//    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
