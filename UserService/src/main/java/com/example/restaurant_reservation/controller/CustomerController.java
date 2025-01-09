package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.CustomerDTO;
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

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomerProfile(@RequestBody CustomerDTO customerDTO, @PathVariable Long customerId) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomerProfile(customerDTO, customerId);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<Long>> getCustomerReservations(@PathVariable Long id) {
        List<Long> reservationIds = customerService.getReservationIdsForCustomer(id);
        return ResponseEntity.ok(reservationIds);
    }

    @PostMapping("/{id}/reservations/{reservationId}")
    public ResponseEntity<Void> addReservationToCustomer(@PathVariable Long id, @PathVariable Long reservationId) {
        customerService.addReservationForCustomer(id, reservationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/reservations/{reservationId}")
    public ResponseEntity<Void> removeReservationFromCustomer(@PathVariable Long id, @PathVariable Long reservationId) {
        customerService.removeReservationForCustomer(id, reservationId);
        return ResponseEntity.noContent().build();
    }

}
