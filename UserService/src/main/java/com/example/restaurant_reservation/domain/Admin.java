package com.example.restaurant_reservation.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Admin extends User {
    private int somethingForAdmin;
    public Admin(String firstName, String lastName, String username, String password, String email, Date birthDate, Address address, Status status, Role role, int somethingForAdmin) {
        super(firstName, lastName, username, password, email, birthDate,0, address, status, role);
        this.somethingForAdmin = somethingForAdmin;
    }
}
