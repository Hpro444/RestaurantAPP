package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.security.service.impl.TokenServiceImpl;
import com.example.restaurant_reservation.service.NotificationService;
import com.example.restaurant_reservation.service.ReservationService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final TokenService tokenService;
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationDTO reservationDTO ,@RequestHeader("Authorization") String authorization) {
        try {
            Long createdReservationId = reservationService.makeReservationForCustomer(reservationDTO.getCustomerId(), reservationDTO.getTableId(), String.valueOf(reservationDTO.getReservationTime()), reservationDTO.getDescription());
            Claims claims = tokenService.parseToken(authorization);
            String email = claims.get("email", String.class);
            String username = claims.get("username", String.class);

            String manager_email = reservationService.getManagerEmailByReservationId(createdReservationId);
            String restaurant_name = reservationService.getRestaurantNameByReservationId(createdReservationId);

            notificationService.sendReservationConfirmationUser(email,username,reservationDTO.getReservationTime());
            notificationService.sendReservationConfirmationManager(manager_email,username,restaurant_name,reservationDTO.getReservationTime());


            return ResponseEntity.ok("Reservation created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // or return a custom error message
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId,@RequestHeader("Authorization") String authorization) {
        Claims claims = tokenService.parseToken(authorization);
        String role = claims.get("role", String.class);
        if (role.equals("MANAGER"))
            return cancelReservationManager(reservationId);
        else
            return cancelReservationUser(reservationId);
    }

    public ResponseEntity<String> cancelReservationManager(Long reservationId) {
        try {
            reservationService.cancelReservationForCustomer(reservationId);
            return ResponseEntity.ok("Reservation cancelled");

        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public ResponseEntity<String> cancelReservationUser(Long reservationId) {
        try {
            reservationService.cancelReservationForCustomer(reservationId);
            return ResponseEntity.ok("Reservation cancelled");

        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
