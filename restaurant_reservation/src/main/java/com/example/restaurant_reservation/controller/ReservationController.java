package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.service.CustomerService;
import com.example.restaurant_reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final CustomerService customerService;
    private final ReservationService reservationService;

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
