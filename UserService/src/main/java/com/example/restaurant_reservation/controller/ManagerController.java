package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.service.ManagerService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
@AllArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(managerService.addRestaurant(restaurantDTO));
    }

    @GetMapping("/{managerId}/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(managerService.getAllRestaurantsByManager(managerId));
    }

    @PostMapping("/restaurants/{restaurantId}/tables")
    public ResponseEntity<TableDTO> addTableToRestaurant(@PathVariable Long restaurantId, @RequestBody TableDTO tableDTO) {
        return ResponseEntity.ok(managerService.addTableToRestaurant(restaurantId, tableDTO));
    }

    @GetMapping("/restaurants/{restaurantId}/tables")
    public ResponseEntity<List<TableDTO>> getAllTablesByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(managerService.getAllTablesByRestaurant(restaurantId));
    }

    @GetMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservationsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(managerService.getAllReservationsForRestaurant(restaurantId));
    }

    @GetMapping("/tables/{tableId}/reservations") // NEW METHOD
    public ResponseEntity<List<ReservationDTO>> getAllReservationsByTable(@PathVariable Long tableId) {
        return ResponseEntity.ok(managerService.getAllReservationsForTable(tableId));
    }
}
