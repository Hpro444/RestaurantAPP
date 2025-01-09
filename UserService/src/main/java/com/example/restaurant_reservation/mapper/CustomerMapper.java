package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Customer;
import com.example.restaurant_reservation.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapper {

    public CustomerDTO getDTOFromDomain(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setNumberOfReservations(customer.getNumberOfReservations());

        return dto;
    }
}
