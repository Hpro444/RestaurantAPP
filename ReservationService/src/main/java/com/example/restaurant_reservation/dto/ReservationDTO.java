package com.example.restaurant_reservation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationDTO {
    private Long id;
    private Long customerId;
    private Long tableId;
    private LocalDateTime reservationTime;
    private String description;
}
