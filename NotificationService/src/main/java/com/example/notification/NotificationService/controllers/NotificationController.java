package com.example.notification.NotificationService.controllers;

import com.example.notification.NotificationService.dto.NotificationRequest;
import com.example.notification.NotificationService.entities.Notification;
import com.example.notification.NotificationService.entities.NotificationType;
import com.example.notification.NotificationService.security.CheckSecurity;
import com.example.notification.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all-notifications")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<Notification>> allNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/send")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = notificationService.sendNotification(
                request.getType(),
                request.getParams(),
                request.getEmail()
        );
        return ResponseEntity.ok(notification);
    }

    // Endpoints for listing, deleting, and updating notifications

    @GetMapping("/types")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationType>> getAllNotificationTypes(@RequestHeader("Authorization") String authorization) {
        List<NotificationType> notificationTypes = notificationService.getAllNotificationTypes();
        return ResponseEntity.ok(notificationTypes);
    }

    @PostMapping("/add-type")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<NotificationType> addNotificationType(@RequestHeader("Authorization") String authorization,@RequestBody NotificationType notificationType) {
        NotificationType newNotificationType = notificationService.addNotificationType(notificationType);
        return ResponseEntity.ok(newNotificationType);
    }

    @PutMapping("/update-type/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<NotificationType> updateNotificationType(@RequestHeader("Authorization") String authorization,@PathVariable Long id, @RequestBody NotificationType updatedNotificationType) {
        NotificationType updatedNotificationTypeResponse = notificationService.updateNotificationType(id, updatedNotificationType);
        return ResponseEntity.ok(updatedNotificationTypeResponse);
    }

    @DeleteMapping("/delete-type/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> deleteNotificationType(@RequestHeader("Authorization") String authorization,@PathVariable Long id) {
        String message = notificationService.deleteNotificationType(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/list-by-type/{type}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<Notification>> listNotificationsByType(@RequestHeader("Authorization") String authorization,@PathVariable String type) {
        List<Notification> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-email")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<Notification>> searchByEmail(@RequestHeader("Authorization") String authorization,@RequestParam String email) {
        List<Notification> notifications = notificationService.searchNotificationsByEmail(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-email/manager")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<List<Notification>> searchByEmailManager(@RequestHeader("Authorization") String authorization,@RequestParam String email) {
        List<Notification> notifications = notificationService.searchNotificationsByEmail(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-email/user")
    @CheckSecurity(roles = {"CUSTOMER"})
    public ResponseEntity<List<Notification>> searchByEmailUser(@RequestHeader("Authorization") String authorization,@RequestParam String email) {
        List<Notification> notifications = notificationService.searchNotificationsByEmail(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-date")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<Notification>> searchByDate(@RequestHeader("Authorization") String authorization,@RequestParam LocalDateTime sentAt) {
        List<Notification> notifications = notificationService.searchNotificationsBySentAt(sentAt);
        return ResponseEntity.ok(notifications);
    }
    @GetMapping("/search-by-dates")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<Notification>> searchByDate(@RequestHeader("Authorization") String authorization,@RequestParam LocalDateTime sentFrom, @RequestParam LocalDateTime sentTo) {
        List<Notification> notifications = notificationService.searchNotificationsBySentAtBetween(sentFrom, sentTo);
        return ResponseEntity.ok(notifications);
    }
}