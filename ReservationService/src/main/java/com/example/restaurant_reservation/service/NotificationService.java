package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.ReservationDTO;

import java.time.LocalDateTime;

public interface NotificationService {
    void sendReservationConfirmationUser(String email,String name, LocalDateTime dateTime);
    void sendReservationConfirmationManager(String email,String name,String restaurant, LocalDateTime dateTime);
    void sendCancellationNotification(String restaurant, LocalDateTime dateTime,String email);
}