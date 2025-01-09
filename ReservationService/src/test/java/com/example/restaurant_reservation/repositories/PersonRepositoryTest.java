package com.example.restaurant_reservation.repositories;

import com.example.restaurant_reservation.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByUsernameAndPassword_ShouldReturnCustomer() {
        // Arrange: Save a customer using CustomerRepository
        Customer customer = new Customer(
                "John", "Doe", "johndoe", "password", "john@example.com",
                new Date(System.currentTimeMillis()), new Address(), Status.ACTIVE, Role.CUSTOMER, 5
        );
        customerRepository.save(customer);

        // Act: Query using PersonRepository
        Optional<Person> found = personRepository.findByUsernameAndPassword("johndoe", "password");

        // Assert: Ensure the found entity is a Customer
        assertTrue(found.isPresent());
        assertInstanceOf(Customer.class, found.get());          /// simplyfied:         assertTrue(found.get() instanceof Customer);
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    void findByUsernameAndPassword_ShouldReturnEmpty_WhenInvalidCredentials() {
        // Arrange: Save a customer
        customerRepository.save(new Customer(
                "Jane", "Doe", "janedoe", "password123", "jane@example.com",
                new Date(System.currentTimeMillis()), new Address(), Status.ACTIVE, Role.CUSTOMER, 5
        ));

        // Act: Query with incorrect credentials
        Optional<Person> found = personRepository.findByUsernameAndPassword("janedoe", "wrongpassword");

        // Assert: Should return empty
        assertFalse(found.isPresent());
    }
}
