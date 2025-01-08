package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.dto.RestaurantDTO;
import com.example.restaurant_reservation.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        try {
            List<CustomerDTO> customers = customerService.findAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        try {
            List<RestaurantDTO> restaurants = customerService.findAllRestaurants();
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long customerId) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomerProfile(customerDTO, customerId);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{customerId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@PathVariable Long customerId) {
        try {
            List<ReservationDTO> reservations = customerService.getReservationsForCustomer(customerId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
