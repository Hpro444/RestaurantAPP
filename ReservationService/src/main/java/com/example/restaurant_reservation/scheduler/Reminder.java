package com.example.restaurant_reservation.scheduler;

import com.example.restaurant_reservation.domain.Reservation;
import com.example.restaurant_reservation.repository.ReservationRepository;
import com.example.restaurant_reservation.service.NotificationService;
import com.example.restaurant_reservation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class Reminder {

    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 5000) // runs every 5 seconds
    public void sendMail() {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            try {
                String email = userService.getEmailForUser(reservation.getCustomerId());
                LocalDateTime now = LocalDateTime.now();
                if (reservation.getAppointment().getDate().toLocalDate().equals(now.toLocalDate()) && reservation.getAppointment().getDate().toLocalTime().getHour() + 1 == now.toLocalTime().getHour() && reservation.getAppointment().getDate().toLocalTime().getMinute() == now.toLocalTime().getMinute()) {
                    notificationService.sendReminderNotification(email);
                }
            } catch (IOException | InterruptedException e) {
                continue;
            }
        }

    }
}