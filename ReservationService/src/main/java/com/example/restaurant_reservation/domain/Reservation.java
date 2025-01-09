package com.example.restaurant_reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity{
    private String description;
    private LocalDateTime reservationTime;

    // Storing CustomerId instead of the full Customer object (like before)
    private Long customerId;

    @ManyToOne
    private TableEntity table;
}
