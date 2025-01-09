package com.example.restaurant_reservation.controllers;

import com.example.restaurant_reservation.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Import for MockMvcRequestBuilders.get


@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getAllRestaurants_ShouldContainBurgerHouseAsFirstRestaurant() throws Exception {
        restaurantRepository.save(new Restaurant(
                "Burger House",
                "Best burgers",
                LocalTime.of(9, 0),
                LocalTime.of(22, 0),
                KitchenType.SERBIAN,
                new Address()
        ));

        mockMvc.perform(get("/api/customers/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[-1].name").value("Burger House")); // Check if the LAST ADDED restaurant matches
    }
}
