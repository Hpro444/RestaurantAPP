package com.example.notification.NotificationService.mapper;

import com.example.notification.NotificationService.dto.NotificationDTO;
import com.example.notification.NotificationService.entities.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationMapper {
    public NotificationDTO getDTOFromDomain(Notification domain) {
        NotificationDTO dto = new NotificationDTO();
        dto.setContent(domain.getContent());
        dto.setType(domain.getType());
        dto.setContent(domain.getContent());
        return dto;
    }

    public Notification getDomainFromDTO(Notification dto) {
        Notification domain = new Notification();
        domain.setContent(dto.getContent());
        domain.setType(dto.getType());
        domain.setContent(dto.getContent());
        return domain;
    }
}
