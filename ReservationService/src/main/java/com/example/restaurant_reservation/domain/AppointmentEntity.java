package com.example.restaurant_reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "is_available", nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private TableEntity table;
}
