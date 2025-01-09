package com.example.restaurant_reservation;

import com.example.restaurant_reservation.domain.Restaurant;
import com.example.restaurant_reservation.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AppRunnerTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void loadData_ShouldPopulateDatabase() {
        List<Customer> customers = customerRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();

        assertEquals(1, customers.size());
        assertEquals(1, restaurants.size());
        assertEquals("Andrija", customers.get(0).getFirstName());
        assertEquals("Smash burgers", restaurants.get(0).getName());
    }
}
