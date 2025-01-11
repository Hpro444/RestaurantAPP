package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.CustomerDTO;

import java.util.List;


public interface CustomerService {

    // Customer profile operations
    CustomerDTO findCustomerById(Long id);
    List<CustomerDTO> findAllCustomers();
    CustomerDTO updateCustomerProfile(CustomerDTO customerDTO, Long customerId);

    // Operations to get reservation IDs
    List<Long> getReservationIdsForCustomer(Long customerId);
    void addReservationForCustomer(Long customerId, Long reservationId);
    void removeReservationForCustomer(Long customerId, Long reservationId);

    // Filtering operations
}
