package com.example.notification.NotificationService.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Service
public class AsyncNotificationService {
    private final NotificationService notificationService;

    @Async
    public void sendNotificationAsync(String type, Map<String, String> params, String email) {
        notificationService.sendNotification(type, params, email);
    }
}
