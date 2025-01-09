package com.example.restaurant_reservation.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    private int numberOfReservations;

//    @OneToMany(mappedBy = "customer")      //  this "customer" reference to the name that is set in Reservation: private Customer ---customer---
//    private List<Reservation> reservations = new ArrayList<>();

    private List<Long> reservationIds = new ArrayList<>(); // List of reservation IDs

    public Customer(String firstName, String lastName, String username, String password, String email, Date birthDate, Address address, Status status, Role role, int numberOfReservations) {
        super(firstName, lastName, username, password, email, birthDate, 0,address, status, role);
        this.numberOfReservations = numberOfReservations;
    }

    public void addReservation(Long reservationId) {
        this.reservationIds.add(reservationId);
        this.numberOfReservations = reservationIds.size();
    }

}
