package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.dto.RestaurantDTO;
import com.example.restaurant_reservation.dto.TableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantDTO getRestaurantById(Long restaurantId);
    RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);
    List<RestaurantDTO> getAllRestaurants();

    RestaurantDTO updateRestaurant(Long restaurantId, RestaurantDTO updatedRestaurantDTO);
}
