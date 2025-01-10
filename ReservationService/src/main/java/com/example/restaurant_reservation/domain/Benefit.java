package com.example.restaurant_reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "benefits")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Benefit extends BaseEntity {
    String description;
    int number_of_needed_reservations;
    Long restaurantId;
}
