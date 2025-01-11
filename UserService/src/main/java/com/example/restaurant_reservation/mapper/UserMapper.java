package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.User;
import com.example.restaurant_reservation.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private AddressMapper addressMapper;

    public UserDTO getDTOFromDomain(User user) {
        UserDTO dto = new UserDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setAddress(addressMapper.getDTOFromDomain(user.getAddress()));
        dto.setBirthDate(user.getBirthDate());
        return dto;
    }
}
