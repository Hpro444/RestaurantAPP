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
            String[] types = {"Activation Email", "Password Change", "Reservation Confirmation Client",
                    "Reservation Confirmation Manager", "Reservation Cancellation", "Reminder Reservation",
                    "Reservation Confirmation User with benefits", "Reservation Confirmation Manager with benefits"};

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
            case "Activation Email" -> "Dear %username,\n\n"
                    + "Thank you for creating an account with us! To complete the registration process and start enjoying all our features, please use this activation code:\n\n"
                    + "Activation Code: **%code**\n\n";
            case "Password Change" ->
                    "Your password has been changed successfully. Please log in with your new credentials";

            case "Reservation Confirmation Client" ->
                    "Good day %name! As your dedicated reservation manager, I'm delighted to confirm that your booking for %date at %time is now finalized. Please feel free to reach out if you have any further requests or questions.";

            case "Reservation Confirmation Manager" ->
                    "ALERT! New reservation incoming! Prepare for invasion by hungry customer %name, descending upon your restaurant %restaurant at %date and %time.";

            case "Reservation Cancellation" ->
                    "Reservation for %restaurant at %date and %time has been cancelled successfully.";

            case "Reminder Reservation" -> "Your reservation is in one hour. Please arrive on time.";

            case "Reservation Confirmation User with benefits" ->
                    "Good day %name! We're thrilled to confirm that your booking for %date at %time is now finalized, and as a valued customer, you'll enjoy our exclusive offer: %benefits!";

            case "Reservation Confirmation Manager with benefits" ->
                    "Exciting News! A new reservation is on the way! Get ready for a hungry costumer %name arrives at %restaurant on %date at %time. This booking comes with an added bonus: %benefits.";
            default -> "";
        };
    }
}