package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.domain.AppointmentEntity;
import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.repository.AppointmentRepository;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.NotificationService;
import com.example.restaurant_reservation.service.ReservationService;
import com.example.restaurant_reservation.service.TableService;
import com.example.restaurant_reservation.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final TokenService tokenService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final TableService tableService;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/{reservationId}")
    @CheckSecurity
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long reservationId, @RequestHeader("Authorization") String authorization) {
        ReservationDTO reservation = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @CheckSecurity
    public ResponseEntity<String> createReservation(@RequestBody ReservationDTO reservationDTO, @RequestHeader("Authorization") String authorization) {
        try {
            Long createdReservationId = reservationService.makeReservationForCustomer(reservationDTO.getCustomerId(), reservationDTO.getTableId(), reservationDTO.getAppointmentID(), reservationDTO.getDescription());
            authorization = authorization.replace("Bearer ", "");
            Claims claims = tokenService.parseToken(authorization);

            String email = claims.get("email", String.class);
            String username = claims.get("username", String.class);
            Long id = claims.get("user_id", Long.class);
            String manager_email = reservationService.getManagerEmailByReservationId(createdReservationId);
            String restaurant_name = reservationService.getRestaurantNameByReservationId(createdReservationId);

            AppointmentEntity appointment = appointmentRepository.findById(reservationDTO.getAppointmentID()).orElseThrow(RuntimeException::new);
            notificationService.sendReservationConfirmationUser(email, username, appointment.getDate(), id, createdReservationId);
            notificationService.sendReservationConfirmationManager(manager_email, username, restaurant_name, appointment.getDate(), id, createdReservationId);

            return ResponseEntity.ok("Reservation created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // or return a custom error message
        }
    }

    @DeleteMapping("/{reservationId}")
    @CheckSecurity
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId, @RequestHeader("Authorization") String authorization) {
        authorization = authorization.replace("Bearer ", "");
        Claims claims = tokenService.parseToken(authorization);
        String role = claims.get("role", String.class);

        if (role.equals("MANAGER"))
            return cancelReservationManager(reservationId);
        else
            return cancelReservationUser(reservationId);
    }

    public ResponseEntity<String> cancelReservationManager(Long reservationId) {
        try {
            ReservationDTO reservation = reservationService.getReservationById(reservationId);
            reservationService.cancelReservationForCustomer(reservationId);

            String email = userService.getEmailForUser(reservation.getCustomerId());
            String restaurant_name = reservationService.getRestaurantNameByReservationId(reservationId);

            AppointmentEntity appointment = appointmentRepository.findById(reservation.getAppointmentID()).orElseThrow(RuntimeException::new);


            notificationService.sendCancellationNotification(restaurant_name, appointment.getDate(), email);

            return ResponseEntity.ok("Reservation cancelled");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> cancelReservationUser(Long reservationId) {
        try {
            ReservationDTO reservation = reservationService.getReservationById(reservationId);
            System.out.println(reservation);

            String email = reservationService.getManagerEmailByReservationId(reservationId);
            String restaurant_name = reservationService.getRestaurantNameByReservationId(reservationId);
            AppointmentEntity appointment = appointmentRepository.findById(reservation.getAppointmentID()).orElseThrow(RuntimeException::new);


            notificationService.sendCancellationNotification(restaurant_name, appointment.getDate(), email);

            reservationService.cancelReservationForCustomer(reservationId);


            return ResponseEntity.ok("Reservation cancelled");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/customer/{customerId}")
    @CheckSecurity
    public ResponseEntity<List<ReservationDTO>> getReservationsForCustomer(@PathVariable Long customerId, @RequestHeader("Authorization") String authorization) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsByCustomer(customerId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/number-of-reservations/{customerId}")
    @CheckSecurity
    public ResponseEntity<Integer> getNumberOfReservationsForCustomer(@PathVariable Long customerId, @RequestHeader("Authorization") String authorization) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsByCustomer(customerId);
            return ResponseEntity.ok(reservations.size());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<List<ReservationDTO>> getReservationsForRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            @RequestHeader("Authorization") String authorization) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsForRestaurantByDateRange(restaurantId, from, to);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
