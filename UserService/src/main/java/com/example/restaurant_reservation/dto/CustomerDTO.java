package com.example.restaurant_reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private AddressDTO address;
}
