package com.example.notification.NotificationService.service;

import com.example.notification.NotificationService.dto.NotificationDTO;
import com.example.notification.NotificationService.dto.NotificationTypeDTO;
import com.example.notification.NotificationService.entities.Notification;
import com.example.notification.NotificationService.entities.NotificationType;
import com.example.notification.NotificationService.mapper.NotificationMapper;
import com.example.notification.NotificationService.mapper.NotificationTypeMapper;
import com.example.notification.NotificationService.repository.NotificationRepository;
import com.example.notification.NotificationService.repository.NotificationTypeRepository;
import com.example.notification.NotificationService.utils.EmailSender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
@AllArgsConstructor
public class NotificationService {

    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationRepository notificationRepository;
    private final EmailSender emailSender;
    private final NotificationTypeMapper notificationTypeMapper;
    private final NotificationMapper notificationMapper;

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(notificationMapper::getDTOFromDomain).toList();
    }

    public List<NotificationTypeDTO> getAllNotificationTypes() {
        return notificationTypeRepository.findAll().stream().map(notificationTypeMapper::getDTOFromDomain).toList();
    }

    public void sendNotification(String type, Map<String, String> params, String email) {
        NotificationType notificationType = notificationTypeRepository.findByName(type)
                .orElseThrow(() -> new RuntimeException("Notification type not found"));
        String content = formatNotification(notificationType.getTemplate(), params);
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setType(type);
        notification.setContent(content);
        notification.setSentAt(LocalDateTime.now());
        try {
            emailSender.sendEmail(email, "Notification: " + type, notification.getContent());
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNotificationType(NotificationTypeDTO newNotificationType) {
        notificationTypeRepository.save(notificationTypeMapper.getDomainFromDTO(newNotificationType));
    }

//    public Notification updateNotification(Long id, Notification updatedNotification) {
//        Notification existingNotification = notificationRepository.findById(id).orElse(null);
//        if (existingNotification != null) {
//            existingNotification.setEmail(updatedNotification.getEmail());
//            existingNotification.setType(updatedNotification.getType());
//            existingNotification.setContent(updatedNotification.getContent());
//            return notificationRepository.save(existingNotification);
//        } else {
//            throw new RuntimeException("Notification not found");
//        }
//    }
//
//    public String deleteNotification(Long id) {
//        notificationRepository.deleteById(id);
//        return "Notification deleted successfully";
//    }

    private String formatNotification(String template, Map<String, String> params) {
        for (Map.Entry<String, String> param : params.entrySet()) {
            template = template.replace("%" + param.getKey(), param.getValue());
        }
        return template;
    }

//    public NotificationType getNotificationType(Long id) {
//        return notificationTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification type not found"));
//    }

    public List<NotificationDTO> getNotificationsByType(String type) {
        return notificationRepository.findByType(type).stream().map(notificationMapper::getDTOFromDomain).toList();
    }

    public String deleteNotificationType(Long id) {
        notificationTypeRepository.deleteById(id);
        return "Notification type deleted successfully";
    }

    public void updateNotificationType(Long id, NotificationTypeDTO updatedNotificationType) {
        NotificationType existingNotificationType = notificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification type not found"));

        existingNotificationType.setName(updatedNotificationType.getName());
        existingNotificationType.setTemplate(updatedNotificationType.getTemplate());

        notificationTypeRepository.save(existingNotificationType);
    }

    public List<NotificationDTO> searchNotificationsByEmail(String email) {
        return notificationRepository.findByEmail(email).stream().map(notificationMapper::getDTOFromDomain).toList();
    }

    public List<NotificationDTO> searchNotificationsBySentAt(LocalDateTime sentAt) {
        return notificationRepository.findBySentAt(sentAt).stream().map(notificationMapper::getDTOFromDomain).toList();
    }

    public List<NotificationDTO> searchNotificationsBySentAtBetween(LocalDateTime from, LocalDateTime to) {
        return notificationRepository.findBySentAtBetween(from, to).stream().map(notificationMapper::getDTOFromDomain).toList();
    }
}