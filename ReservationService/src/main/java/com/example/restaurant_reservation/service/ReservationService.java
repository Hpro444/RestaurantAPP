package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.domain.Reservation;
import com.example.restaurant_reservation.dto.ReservationDTO;

public interface ReservationService {
    ReservationDTO makeReservationForCustomer(Long customerId, Long tableEntityId, String reservationDate);
    void cancelReservationForCustomer(Long reservationId);

    ReservationDTO getReservationById(Long id);
}
