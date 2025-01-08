package com.example.notification.NotificationService.service;

import com.example.notification.NotificationService.entities.*;
import com.example.notification.NotificationService.repository.*;
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
    private EmailSender emailSender;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<NotificationType> getAllNotificationTypes() {
        return notificationTypeRepository.findAll();
    }

    public Notification sendNotification(String type, Map<String, String> params, String email) {
        NotificationType notificationType = notificationTypeRepository.findByName(type)
                .orElseThrow(() -> new RuntimeException("Notification type not found"));
        String content = formatNotification(notificationType.getTemplate(), params);
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setType(type);
        notification.setContent(content);
        notification.setSentAt(LocalDateTime.now());
        try {
            emailSender.sendEmail( email, "Notification: " + type, notification.getContent());
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        return notificationRepository.save(notification);
    }

    public NotificationType addNotificationType(NotificationType newNotificationType) {
        return notificationTypeRepository.save(newNotificationType);
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        Notification existingNotification = notificationRepository.findById(id).orElse(null);
        if (existingNotification != null) {
            existingNotification.setEmail(updatedNotification.getEmail());
            existingNotification.setType(updatedNotification.getType());
            existingNotification.setContent(updatedNotification.getContent());
            return notificationRepository.save(existingNotification);
        } else {
            throw new RuntimeException("Notification not found");
        }
    }

    public String deleteNotification(Long id) {
        notificationRepository.deleteById(id);
        return "Notification deleted successfully";
    }

    private String formatNotification(String template, Map<String, String> params) {
        for (Map.Entry<String, String> param : params.entrySet()) {
            template = template.replace("%" + param.getKey(), param.getValue());
        }
        return template;
    }

    public NotificationType getNotificationType(Long id) {
        return notificationTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification type not found"));
    }

    public List<Notification> getNotificationsByType(String type) {
        return notificationRepository.findByType(type);
    }

    public String deleteNotificationType(Long id) {
        notificationTypeRepository.deleteById(id);
        return "Notification type deleted successfully";
    }
    public NotificationType updateNotificationType(Long id, NotificationType updatedNotificationType) {
        NotificationType existingNotificationType = notificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification type not found"));

        existingNotificationType.setName(updatedNotificationType.getName());
        existingNotificationType.setTemplate(updatedNotificationType.getTemplate());

        return notificationTypeRepository.save(existingNotificationType);
    }

    public List<Notification> searchNotificationsByEmail(String email) {
        return notificationRepository.findByEmail(email);
    }

    public List<Notification> searchNotificationsBySentAt(LocalDateTime sentAt) {
        return notificationRepository.findBySentAt(sentAt);
    }
    public List<Notification> searchNotificationsBySentAtBetween(LocalDateTime from, LocalDateTime to) {
        return notificationRepository.findBySentAtBetween(from, to);
    }
}