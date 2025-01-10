package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Benefit;
import com.example.restaurant_reservation.dto.BenefitDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BenefitMapper {
    public Benefit getDomainFromDTO(BenefitDTO dto) {
        Benefit benefit = new Benefit();
        benefit.setDescription(dto.getDescription());
        benefit.setRestaurantId(dto.getRestaurantId());
        benefit.setNumber_of_needed_reservations(dto.getNumber_of_needed_reservations());
        return benefit;
    }

    public BenefitDTO getDTOFromDomain(Benefit item) {
        BenefitDTO dto = new BenefitDTO();
        dto.setDescription(item.getDescription());
        dto.setRestaurantId(item.getRestaurantId());
        dto.setNumber_of_needed_reservations(item.getNumber_of_needed_reservations());
        return dto;
    }
}
