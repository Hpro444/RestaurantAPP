package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantDTO getRestaurantById(Long restaurantId);

    void addRestaurant(RestaurantDTO restaurantDTO);

    List<RestaurantDTO> getAllRestaurants();

    void updateRestaurant(Long restaurantId, RestaurantDTO updatedRestaurantDTO);
}
