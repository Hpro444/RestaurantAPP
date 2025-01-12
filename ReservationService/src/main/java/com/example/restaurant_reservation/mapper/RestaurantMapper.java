package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Restaurant;
import com.example.restaurant_reservation.dto.RestaurantDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestaurantMapper {

    public RestaurantDTO getDTOFromDomain(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setAddress(restaurant.getAddress());
        dto.setKitchenType(restaurant.getKitchenType());     // enum to string
        dto.setOpeningTime(restaurant.getOpeningTime());
        dto.setClosingTime(restaurant.getClosingTime());
        dto.setManagerEmail(restaurant.getManagerEmail());
        dto.setManagerId(restaurant.getManagerId());
        return dto;
    }

    //    public Restaurant getDomainFromDTO(RestaurantDTO dto) {
//
//        return new Restaurant(dto.getManagerId(), dto.getName(), dto.getManagerEmail(), dto.getDescription(), dto.getOpeningTime(), dto.getClosingTime(), dto.getKitchenType(), dto.getAddress());
//    }
    public Restaurant getDomainFromDTO(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setManagerId(dto.getManagerId());
        restaurant.setName(dto.getName());
        restaurant.setManagerEmail(dto.getManagerEmail());
        restaurant.setDescription(dto.getDescription());
        restaurant.setOpeningTime(dto.getOpeningTime());
        restaurant.setClosingTime(dto.getClosingTime());
        restaurant.setKitchenType(dto.getKitchenType());
        restaurant.setAddress(dto.getAddress());
        return restaurant;
    }

}
