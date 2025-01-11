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

    @Override
    public CustomerDTO findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.getDTOFromDomain(customer);
    }

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

    @Override
    public List<Long> getReservationIdsForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getReservationIds();
    }

    @Override
    public void addReservationForCustomer(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.getReservationIds().add(reservationId);
        customerRepository.save(customer);
    }

    @Override
    public void removeReservationForCustomer(Long customerId, Long reservationId) {     // TODO: add the check if the reservation time is less than 3h
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.getReservationIds().remove(reservationId);
        customerRepository.save(customer);
    }


}
