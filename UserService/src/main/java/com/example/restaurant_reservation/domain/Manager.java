package com.example.restaurant_reservation.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@DiscriminatorValue("MANAGER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends User {
    private Date dateOfStartWorking;

    public Manager(Date dateOfStartWorking, String firstName, String lastName, String username, String password,
                   String email, Date birthDate, Address address, Status status, Role role) {
        super(firstName, lastName, username, password, email, birthDate, 0, address, status, role);
        this.dateOfStartWorking = dateOfStartWorking;
    }
}
