package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Customer;
import com.example.restaurant_reservation.domain.User;
import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.UpdateDTO;
import com.example.restaurant_reservation.mapper.AddressMapper;
import com.example.restaurant_reservation.mapper.CustomerMapper;
import com.example.restaurant_reservation.repository.UserRepository;
import com.example.restaurant_reservation.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final CustomerMapper customerMapper;

    private final AddressMapper addressMapper;


    @Override
    public List<CustomerDTO> findAllCustomers() {
        return userRepository.findAll().stream().map(t -> (Customer) t)
                .map(customerMapper::getDTOFromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCustomerProfile(UpdateDTO customerDTO, Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customer.getEmail());
        customer.setAddress(addressMapper.getDomainFromDTO(customerDTO.getAddress()));
        customer.setBirthDate(customerDTO.getBirthDate());

        userRepository.save(customer);

    }

}
