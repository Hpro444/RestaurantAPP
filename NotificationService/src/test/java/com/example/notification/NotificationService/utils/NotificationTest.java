package com.example.notification.NotificationService.utils;

import com.example.notification.NotificationService.config.RabbitConfig;
import com.example.notification.NotificationService.dto.NotificationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.HashMap;

@SpringBootTest
@Import(RabbitConfig.class)
public class NotificationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendNotification() {
        NotificationRequest request = new NotificationRequest();
        request.setType("Activation Email");
        HashMap<String, String> data = new HashMap<>();
        data.put("name", "Mateja");
        data.put("surname","Subin");
        request.setParams(data);
        request.setEmail("hpro444@gmail.com");

        rabbitTemplate.convertAndSend("notification-queue", request);
        System.out.println("Notification sent successfully!");
    }
}