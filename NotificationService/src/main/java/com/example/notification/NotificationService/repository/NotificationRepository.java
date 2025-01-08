package com.example.notification.NotificationService.repository;

import com.example.notification.NotificationService.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByType(String type);
    List<Notification> findByEmail(String email);
    List<Notification> findBySentAt(LocalDateTime sentAt);
    List<Notification> findBySentAtBetween(LocalDateTime from, LocalDateTime to);
}