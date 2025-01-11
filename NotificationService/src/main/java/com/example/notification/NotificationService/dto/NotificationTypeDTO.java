package com.example.notification.NotificationService.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationTypeDTO {
    private String name; // e.g., "Activation Email", "Password Change"
    private String template; // e.g., "Greetings %name %surname, Your reservation for %date at %time has been confirmed"
}
