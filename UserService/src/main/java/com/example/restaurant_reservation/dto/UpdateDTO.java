package com.example.restaurant_reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UpdateDTO {
    String firstName;
    String lastName;
    AddressDTO address;
    Date birthDate;

}
