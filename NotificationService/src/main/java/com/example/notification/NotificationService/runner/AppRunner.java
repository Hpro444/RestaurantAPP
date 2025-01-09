package com.example.notification.NotificationService.runner;

import com.example.notification.NotificationService.entities.NotificationType;
import com.example.notification.NotificationService.repository.NotificationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class AppRunner {

    private final NotificationTypeRepository notificationTypeRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            String[] types = {"Activation Email", "Password Change", "Reservation Confirmation Client","Reservation Confirmation Manager",
                    "Reservation Cancellation", "Reminder Reservation"};

            Arrays.stream(types)
                    .forEach(type -> addNotificationType(notificationTypeRepository, type));
        };
    }

    private void addNotificationType(NotificationTypeRepository notificationTypeRepository, String type) {
        NotificationType notificationType = new NotificationType();
        notificationType.setName(type);
        notificationType.setTemplate(getTemplate(type));

        notificationTypeRepository.save(notificationType);

        System.out.println("Added notification type: " + type);
    }

    private static String getTemplate(String type) {
        return switch (type) {
            case "Activation Email" -> "Greetings %name %surname, Your account has been activated";
            case "Password Change" ->
                    "Your password has been changed successfully. Please log in with your new credentials";
            case "Reservation Confirmation Client" ->
                    "Good day %name! As your dedicated reservation manager, I'm delighted to confirm that your booking for %date at %time is now finalized. Please feel free to reach out if you have any further requests or questions.";
            case "Reservation Confirmation Manager" ->
                    "ALERT! New reservation incoming! Prepare for invasion by hungry customer %name, descending upon your restaurant %restaurant at %date and %time.";
            case "Reservation Cancellation" ->
                    "Reservation for %restaurant at %date and %time has been cancelled successfully.";
            case "Reminder Reservation" -> "Your reservation is in one hour. Please arrive on time.";
            default -> "";
        };
    }
}