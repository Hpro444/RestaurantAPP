package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Customer;
import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.mapper.CustomerMapper;
import com.example.restaurant_reservation.repository.CustomerRepository;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    private TokenService tokenService;


    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> customerMapper.getDTOFromDomain(customer))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomerProfile(CustomerDTO customerDTO, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        // existingCustomer.setEmail(customer.getEmail());       could be very complicated, we ll do it in the end

        Customer updatedCustomer = customerRepository.save(customer);

        CustomerDTO dto = customerMapper.getDTOFromDomain(updatedCustomer);
        return dto;
    }

//    @Override
//    public List<RestaurantDTO> findAllRestaurants() {
//        return restaurantRepository.findAll().stream()
//                .map(restaurant -> restaurantMapper.getDTOFromDomain(restaurant))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public RestaurantDTO findRestaurantById(Long id) {
//        return restaurantRepository.findById(id)
//                .map(restaurant -> restaurantMapper.getDTOFromDomain(restaurant))
//                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
//    }
//
//    @Override
//    public List<ReservationDTO> getReservationsForCustomer(Long customerId) {
//        return customerRepository.findById(customerId)
//                .map(Customer::getReservations)
//                .orElseThrow(() -> new RuntimeException("Customer not found"))
//                .stream()
//                .map(reservation -> reservationMapper.getDTOFromDomain(reservation))
//                .collect(Collectors.toList());
//    }

}
