package com.example.restaurant_reservation.dto;

import com.example.restaurant_reservation.domain.Address;
import com.example.restaurant_reservation.domain.KitchenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String name;
    private String managerEmail;
    private Long managerId;
    private String description;
    private Address address;
    private KitchenType kitchenType;
    private LocalTime openingTime;
    private LocalTime closingTime;

}
