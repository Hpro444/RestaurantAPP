package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.UpdateDTO;

import java.util.List;


public interface CustomerService {

    // Customer profile operations
    List<CustomerDTO> findAllCustomers();

    void updateCustomerProfile(UpdateDTO customerDTO, Long customerId);

}
