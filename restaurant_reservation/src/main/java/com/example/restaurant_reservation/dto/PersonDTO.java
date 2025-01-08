package com.example.restaurant_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role; // CUSTOMER, MANAGER, ADMIN
    private Date birthDate;
}