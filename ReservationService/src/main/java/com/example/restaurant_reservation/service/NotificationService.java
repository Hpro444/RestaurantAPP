package com.example.restaurant_reservation.service;

import java.time.LocalDateTime;

public interface NotificationService {
    void sendReservationConfirmationUser(String email, String name, LocalDateTime dateTime, Long user_id, Long restaurant_id);

    void sendReservationConfirmationManager(String email, String name, String restaurant, LocalDateTime dateTime, Long user_id, Long restaurant_id);

    void sendCancellationNotification(String restaurant, LocalDateTime dateTime, String email);

    void sendReminderNotification(String email);
}