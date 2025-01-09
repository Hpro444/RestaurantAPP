package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.dto.NotificationRequest;
import com.example.restaurant_reservation.service.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    @Async
    public void sendReservationConfirmationUser(String email,String name, LocalDateTime dateTime) {
        NotificationRequest request = new NotificationRequest();
        request.setType("Reservation Confirmation");
        HashMap<String, String> data = new HashMap<>();

        data.put("name", name);
        data.put("time",dateTime.toLocalTime().toString());
        data.put("date",dateTime.toLocalDate().toString());

        request.setParams(data);
        request.setEmail(email);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }

    @Override
    @Async
    public void sendReservationConfirmationManager(String email, String name,String restaurant, LocalDateTime dateTime) {
        NotificationRequest request = new NotificationRequest();
        request.setType("Reservation Confirmation");
        HashMap<String, String> data = new HashMap<>();

        data.put("name", name);
        data.put("time",dateTime.toLocalTime().toString());
        data.put("date",dateTime.toLocalDate().toString());
        data.put("restaurant",restaurant);

        request.setParams(data);
        request.setEmail(email);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }

    @Override
    @Async

    public void sendCancellationNotification(String restaurant, LocalDateTime dateTime, String email) {
        NotificationRequest request = new NotificationRequest();
        request.setType("Reservation Cancellation");
        HashMap<String, String> data = new HashMap<>();

        data.put("time",dateTime.toLocalTime().toString());
        data.put("date",dateTime.toLocalDate().toString());
        data.put("restaurant",restaurant);

        request.setParams(data);
        request.setEmail(email);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }
}