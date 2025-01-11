package com.example.notification.NotificationService.controllers;

import com.example.notification.NotificationService.dto.NotificationDTO;
import com.example.notification.NotificationService.dto.NotificationRequest;
import com.example.notification.NotificationService.dto.NotificationTypeDTO;
import com.example.notification.NotificationService.security.CheckSecurity;
import com.example.notification.NotificationService.security.service.TokenService;
import com.example.notification.NotificationService.service.NotificationService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final TokenService tokenService;

    @GetMapping("/all-notifications")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationDTO>> allNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/send")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            notificationService.sendNotification(request.getType(), request.getParams(), request.getEmail());
            return ResponseEntity.ok("Notification sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // Endpoints for listing, deleting, and updating notifications

    @GetMapping("/types")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationTypeDTO>> getAllNotificationTypes(@RequestHeader("Authorization") String authorization) {
        List<NotificationTypeDTO> notificationTypes = notificationService.getAllNotificationTypes();
        return ResponseEntity.ok(notificationTypes);
    }

    @PostMapping("/add-type")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> addNotificationType(@RequestHeader("Authorization") String authorization, @RequestBody NotificationTypeDTO notificationType) {
        try {
            notificationService.addNotificationType(notificationType);
            return ResponseEntity.ok("NotificationType added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update-type/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> updateNotificationType(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestBody NotificationTypeDTO updatedNotificationType) {
        try {
            notificationService.updateNotificationType(id, updatedNotificationType);
            return ResponseEntity.ok("NotificationType updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-type/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> deleteNotificationType(@RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        String message = notificationService.deleteNotificationType(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/list-by-type/{type}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationDTO>> listNotificationsByType(@RequestHeader("Authorization") String authorization, @PathVariable String type) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-email")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationDTO>> searchByEmail(@RequestHeader("Authorization") String authorization, @RequestParam String email) {
        List<NotificationDTO> notifications = notificationService.searchNotificationsByEmail(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-email/manager")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<List<NotificationDTO>> searchByEmailManager(@RequestHeader("Authorization") String authorization) {
        try {
            authorization = authorization.replace("Bearer ", "");
            Claims claims = tokenService.parseToken(authorization);
            String email = claims.get("email", String.class);
            List<NotificationDTO> notifications = notificationService.searchNotificationsByEmail(email);
            return ResponseEntity.ok(notifications);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search-by-email/user")
    @CheckSecurity(roles = {"CUSTOMER"})
    public ResponseEntity<List<NotificationDTO>> searchByEmailUser(@RequestHeader("Authorization") String authorization, @RequestParam String email) {
        List<NotificationDTO> notifications = notificationService.searchNotificationsByEmail(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-date")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationDTO>> searchByDate(@RequestHeader("Authorization") String authorization, @RequestParam LocalDateTime sentAt) {
        List<NotificationDTO> notifications = notificationService.searchNotificationsBySentAt(sentAt);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search-by-dates")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<NotificationDTO>> searchByDate(@RequestHeader("Authorization") String authorization, @RequestParam LocalDateTime sentFrom, @RequestParam LocalDateTime sentTo) {
        List<NotificationDTO> notifications = notificationService.searchNotificationsBySentAtBetween(sentFrom, sentTo);
        return ResponseEntity.ok(notifications);
    }
}