package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Benefit;
import com.example.restaurant_reservation.dto.NotificationRequest;
import com.example.restaurant_reservation.repository.BenefitRepository;
import com.example.restaurant_reservation.service.NotificationService;
import com.example.restaurant_reservation.service.ReservationService;
import com.example.restaurant_reservation.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final ReservationService reservationService;
    private final RestaurantService restaurantService;
    private final BenefitRepository benefitRepository;


    @Override
    @Async
    public void sendReservationConfirmationUser(String email, String name, LocalDateTime dateTime, Long user_id, Long restaurant_id) {
        NotificationRequest request = new NotificationRequest();

        int reservation_count = reservationService.getReservationsByCustomer(user_id).size() + 1;
        System.out.println(reservation_count);
        List<Benefit> benefits = benefitRepository.getBenefitsByRestaurantIdAndNumberOfNeededReservations(restaurant_id, reservation_count).orElse(List.of());
        HashMap<String, String> data = new HashMap<>();

        if (!benefits.isEmpty()) {
            String benefitDescription = benefits.stream().map(Benefit::getDescription).collect(Collectors.joining(", "));
            request.setType("Reservation Confirmation User with benefits");
            data.put("benefits", benefitDescription);
        } else
            request.setType("Reservation Confirmation Client");

        data.put("name", name);
        data.put("time", dateTime.toLocalTime().toString());
        data.put("date", dateTime.toLocalDate().toString());

        request.setParams(data);
        request.setEmail(email);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }

    @Override
    @Async
    public void sendReservationConfirmationManager(String email, String name, String restaurant, LocalDateTime dateTime, Long user_id, Long restaurant_id) {
        NotificationRequest request = new NotificationRequest();

        int reservation_count = reservationService.getReservationsByCustomer(user_id).size() + 1;

        List<Benefit> benefits = benefitRepository.getBenefitsByRestaurantIdAndNumberOfNeededReservations(restaurant_id, reservation_count).orElse(List.of());
        HashMap<String, String> data = new HashMap<>();

        if (!benefits.isEmpty()) {
            String benefitDescription = benefits.stream().map(Benefit::getDescription).collect(Collectors.joining(", "));
            request.setType("Reservation Confirmation Manager with benefits");
            data.put("benefits", benefitDescription);
        } else
            request.setType("Reservation Confirmation Manager");

        data.put("name", name);
        data.put("time", dateTime.toLocalTime().toString());
        data.put("date", dateTime.toLocalDate().toString());
        data.put("restaurant", restaurant);

        request.setParams(data);
        request.setEmail(email);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }

    @Override
    @Async

    public void sendCancellationNotification(String restaurant, LocalDateTime dateTime, String email) {
        NotificationRequest request = new NotificationRequest();
        request.setType("Reservation Cancellation");
        HashMap<String, String> data = new HashMap<>();

        data.put("time", dateTime.toLocalTime().toString());
        data.put("date", dateTime.toLocalDate().toString());
        data.put("restaurant", restaurant);

        request.setParams(data);
        request.setEmail(email);
        rabbitTemplate.convertAndSend("notification-queue", request);
    }

    @Override
    public void sendReminderNotification(String email) {
        NotificationRequest request = new NotificationRequest();
        request.setParams(new HashMap<>());
        request.setType("Reminder Reservation");
        request.setEmail(email);
        
        rabbitTemplate.convertAndSend("notification-queue", request);
    }
}