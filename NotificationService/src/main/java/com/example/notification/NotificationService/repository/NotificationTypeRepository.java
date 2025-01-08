package com.example.notification.NotificationService.repository;

import com.example.notification.NotificationService.entities.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    // Method to find NotificationType by name
    Optional<NotificationType> findByName(String name);
}
