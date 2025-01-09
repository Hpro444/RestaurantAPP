package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.service.ReservationService;
import com.example.restaurant_reservation.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


}
