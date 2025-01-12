package com.example.restaurant_reservation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationDTO {
    private Long id;
    private boolean deleted;
    private Long customerId;
    private Long tableId;
    private Long appointmentID;
    private String description;
}
