package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    // Customer profile operations
    List<CustomerDTO> findAllCustomers();
    CustomerDTO updateCustomerProfile(CustomerDTO customerDTO, Long customerId);

    // Restaurant related operations
    List<RestaurantDTO> findAllRestaurants();
    RestaurantDTO findRestaurantById(Long id);

    // Reservation-related operations
    List<ReservationDTO> getReservationsForCustomer(Long customerId);
 }
