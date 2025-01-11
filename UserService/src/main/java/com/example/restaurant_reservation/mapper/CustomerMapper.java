package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Customer;
import com.example.restaurant_reservation.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapper {
    private final AddressMapper addressMapper;

    public CustomerDTO getDTOFromDomain(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(addressMapper.getDTOFromDomain(customer.getAddress()));
        dto.setBirthDate(customer.getBirthDate());
        return dto;
    }
}
