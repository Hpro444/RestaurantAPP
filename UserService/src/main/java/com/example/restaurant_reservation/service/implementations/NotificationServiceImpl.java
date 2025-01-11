package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.dto.NotificationRequest;
import com.example.restaurant_reservation.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @Override
    public void sendRegistrationNotification(String name, String surname, String email, String code) {
        NotificationRequest request = new NotificationRequest();
        request.setType("Activation Email");
        request.setEmail(email);
        HashMap<String, String> data = new HashMap<>();
        data.put("username", name + " " + surname);
        data.put("code", code);
        request.setParams(data);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }
}