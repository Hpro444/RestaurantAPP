package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Restaurant;
import com.example.restaurant_reservation.dto.RestaurantDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class RestaurantMapper {

    public RestaurantDTO getDTOFromDomain(Restaurant restaurant){
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setAddress(restaurant.getAddress());
        dto.setKitchenType(restaurant.getKitchenType());     // enum to string
        dto.setOpeningTime(restaurant.getOpeningTime());
        dto.setClosingTime(restaurant.getClosingTime());

        return dto;
    }

}
