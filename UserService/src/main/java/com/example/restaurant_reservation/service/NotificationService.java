package com.example.restaurant_reservation.service;

public interface NotificationService {
    void sendRegistrationNotification(String name, String surname, String email, String code);
}