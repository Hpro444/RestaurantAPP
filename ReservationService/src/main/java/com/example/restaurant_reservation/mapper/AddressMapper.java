package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Address;
import com.example.restaurant_reservation.dto.AddressDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressMapper {

    public AddressDTO getDTOFromDomain(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZip(address.getZip());
        return dto;
    }

    public Address getDomainFromDTO(AddressDTO address) {
        Address domain = new Address();
        domain.setCity(address.getCity());
        domain.setState(address.getState());
        domain.setZip(address.getZip());
        return domain;
    }
}
