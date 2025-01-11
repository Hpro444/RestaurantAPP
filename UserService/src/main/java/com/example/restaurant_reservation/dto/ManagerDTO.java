package com.example.restaurant_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private AddressDTO address;
    private Date dateOfStartWorking;
}
