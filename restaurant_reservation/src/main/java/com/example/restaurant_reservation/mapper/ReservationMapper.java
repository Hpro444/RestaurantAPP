package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.Reservation;
import com.example.restaurant_reservation.dto.ReservationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationMapper {

    public ReservationDTO getDTOFromDomain(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setCustomerId(reservation.getCustomer().getId());
        dto.setTableId(reservation.getTable().getId());
        dto.setReservationTime(reservation.getReservationTime());

        return dto;
    }
}
