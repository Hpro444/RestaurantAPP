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
            String[] types = {"Activation Email", "Password Change", "Reservation Confirmation",
                    "Reservation Cancellation Client", "Reservation Cancellation Manager", "Reminder Reservation"};

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
        switch (type) {
            case "Activation Email":
                return "Greetings %name %surname, Your account has been activated";
            case "Password Change":
                return "Your password has been changed successfully. Please log in with your new credentials";
            case "Reservation Confirmation":
                return "Greetings %name %surname, Your reservation for %date at %time has been confirmed";
            case "Reservation Cancellation Client":
                return "Your reservation has been cancelled successfully.";
            case "Reservation Cancellation Manager":
                return "A client's reservation has been cancelled at your restaurant.";
            case "Reminder Reservation":
                return "Your reservation is in one hour. Please arrive on time.";
            default:
                return "";
        }
    }
}