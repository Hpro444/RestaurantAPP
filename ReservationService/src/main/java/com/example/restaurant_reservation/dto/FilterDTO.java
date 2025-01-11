package com.example.restaurant_reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class FilterDTO {
    private String kitchenType;
    private AddressDTO address;
    private int capacity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
