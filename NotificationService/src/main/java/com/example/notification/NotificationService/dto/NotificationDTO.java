package com.example.notification.NotificationService.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {
    private String email;
    private String type; // e.g., "Activation Email"
    private String content; // Actual content sent

    private LocalDateTime sentAt;
}
