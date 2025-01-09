package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
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
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId,@RequestHeader("Authorization") String authorization) {
        reservationService.cancelReservationForCustomer(reservationId);
        return ResponseEntity.noContent().build();
    }
}
