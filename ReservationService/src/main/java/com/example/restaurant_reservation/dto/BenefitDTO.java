package com.example.restaurant_reservation.dto;

import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class BenefitDTO {
    String description;
    int number_of_needed_reservations;
    Long restaurantId;
}
