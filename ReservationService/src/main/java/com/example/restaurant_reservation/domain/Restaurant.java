package com.example.restaurant_reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "restaurants")
@DiscriminatorColumn
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity {

    private Long managerId;

    @Column(name = "manager_email", nullable = false)
    private String managerEmail;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime;

    @Column(name = "closing_time", nullable = false)
    private LocalTime closingTime;

    @Enumerated(EnumType.STRING)
    private KitchenType kitchenType;

    @Embedded
    private Address address;
}
