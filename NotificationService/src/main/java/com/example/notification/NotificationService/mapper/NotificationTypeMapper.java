package com.example.notification.NotificationService.mapper;

import com.example.notification.NotificationService.dto.NotificationTypeDTO;
import com.example.notification.NotificationService.entities.NotificationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationTypeMapper {

    public NotificationTypeDTO getDTOFromDomain(NotificationType domain) {
        NotificationTypeDTO dto = new NotificationTypeDTO();
        dto.setName(domain.getName());
        dto.setTemplate(domain.getTemplate());
        return dto;
    }

    public NotificationType getDomainFromDTO(NotificationTypeDTO dto) {
        NotificationType domain = new NotificationType();
        domain.setName(dto.getName());
        domain.setTemplate(dto.getTemplate());
        return domain;
    }
}
