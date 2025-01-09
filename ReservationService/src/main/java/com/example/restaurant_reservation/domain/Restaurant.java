package com.example.restaurant_reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="restaurants")
@DiscriminatorColumn
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity{

    private Long managerId;
    private String manager_email;

    private String name;
    private String description;

    private LocalTime openingTime;
    private LocalTime closingTime;
    //    private String location;   would be fun

    @Enumerated(value = EnumType.STRING)
    private KitchenType kitchenType;

    @Embedded
    private Address address;



}
