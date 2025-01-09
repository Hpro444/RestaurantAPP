package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.domain.Reservation;
import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Because we split 1 service into 2
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO createdReservation = reservationService.makeReservationForCustomer(
                    reservationDTO.getCustomerId(),
                    reservationDTO.getTableId(),
                    String.valueOf(reservationDTO.getReservationTime())
            );
            return ResponseEntity.ok(createdReservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // or return a custom error message
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId,
                                                  @RequestHeader("Authorization") String authorization) {
        // Pass the token to the service
        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : null;
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        reservationService.cancelReservationForCustomer(reservationId, token);
        return ResponseEntity.noContent().build();
    }
}
