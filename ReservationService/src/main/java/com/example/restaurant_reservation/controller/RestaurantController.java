package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.BenefitDTO;
import com.example.restaurant_reservation.dto.FilterDTO;
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
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> createRestaurant(@RequestHeader("Authorization") String authorization, @RequestBody RestaurantDTO restaurantDTO) {
        try {
            restaurantService.addRestaurant(restaurantDTO);
            return ResponseEntity.ok("Restaurant created successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> updateRestaurant(@RequestHeader("Authorization") String authorization, @PathVariable long id, @RequestBody RestaurantDTO restaurantDTO) {
        try {
            restaurantService.updateRestaurant(id, restaurantDTO);
            return ResponseEntity.ok("Restaurant created successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/benefits")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<List<BenefitDTO>> getBenefitsForRestaurant(@RequestHeader("Authorization") String authorization, @PathVariable long id) {
        try {
            List<BenefitDTO> list = restaurantService.getAllBenefitsForRestaurant(id);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{id}/benefits")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> addBenefitToRestaurant(@RequestHeader("Authorization") String authorization, @PathVariable long id, @RequestBody BenefitDTO benefitDTO) {
        try {
            restaurantService.addBenefitToRestaurant(id, benefitDTO);
            return ResponseEntity.ok("Benefit added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Benefit not added");
        }
    }

    @DeleteMapping("/{id}/benefits/{benefit_id}")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> removeBenefitFromRestaurant(@RequestHeader("Authorization") String authorization, @PathVariable long id, @PathVariable long benefit_id) {
        try {
            restaurantService.removeBenefitFromRestaurant(benefit_id);
            return ResponseEntity.ok("Benefit removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Benefit not removed");
        }
    }

    @GetMapping("/filter-appointments")
    @CheckSecurity
    public ResponseEntity<List<AppointmentDTO>> getFilteredAppointment(@RequestHeader("Authorization") String authorization, FilterDTO filterDTO) {
        try {
            return ResponseEntity.ok().body(restaurantService.getAllAppointmentsByFilter(filterDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
