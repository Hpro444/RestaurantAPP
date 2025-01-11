package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.UpdateDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
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
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(@RequestHeader("Authorization") String authorization) {
        try {
            List<CustomerDTO> customers = customerService.findAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{customerId}")
    @CheckSecurity
    public ResponseEntity<String> updateCustomerProfile(@RequestHeader("Authorization") String authorization, @RequestBody UpdateDTO customerDTO, @PathVariable Long customerId) {
        try {
            customerService.updateCustomerProfile(customerDTO, customerId);
            return ResponseEntity.ok("Successfully updated customer profile");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
