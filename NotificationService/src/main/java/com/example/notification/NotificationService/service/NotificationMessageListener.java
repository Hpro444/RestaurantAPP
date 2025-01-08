package com.example.notification.NotificationService.service;

import com.example.notification.NotificationService.dto.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@Service
public class NotificationMessageListener {
    private final AsyncNotificationService asyncNotificationService;

    @RabbitListener(queues = "notification-queue")
    public void receiveMessage(NotificationRequest request) {
        asyncNotificationService.sendNotificationAsync(request.getType(), request.getParams(), request.getEmail());
    }
}
