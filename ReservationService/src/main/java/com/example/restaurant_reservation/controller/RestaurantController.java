package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.RestaurantDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    @CheckSecurity
    public ResponseEntity<RestaurantDTO> getRestaurantById(@RequestHeader("Authorization") String authorization, @PathVariable long id) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurantDTO);
    }

    @GetMapping
    @CheckSecurity
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(@RequestHeader("Authorization") String authorization) {
        List<RestaurantDTO> restaurantDTOList = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurantDTOList);
    }

    @PostMapping
    @CheckSecurity
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestHeader("Authorization") String authorization, @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO createdRestaurant = restaurantService.addRestaurant(restaurantDTO);
        return ResponseEntity.ok(createdRestaurant);
    }

    @PutMapping("/{id}")
    @CheckSecurity
    public ResponseEntity<RestaurantDTO> updateRestaurant(@RequestHeader("Authorization") String authorization, @PathVariable long id, @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

}
