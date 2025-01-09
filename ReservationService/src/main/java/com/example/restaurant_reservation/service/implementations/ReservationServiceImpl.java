package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Reservation;
import com.example.restaurant_reservation.domain.TableEntity;
import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.mapper.ReservationMapper;
import com.example.restaurant_reservation.repository.ReservationRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.ReservationService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private TableRepository tableRepository;

    private ReservationMapper reservationMapper;


    @Override
    public ReservationDTO makeReservationForCustomer(Long customerId, Long tableEntityId, String reservationDate) {

        // Validate the reservation date and time
        LocalDateTime reservationTime = LocalDateTime.parse(reservationDate);

        // Create a new ReservationDTO
        ReservationDTO reservation = new ReservationDTO();
        reservation.setCustomerId(customerId);
        reservation.setTableId(tableEntityId);
        reservation.setReservationTime(reservationTime);

        return reservation;
    }

    @Override
    public void cancelReservationForCustomer(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));


            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime reservationTime = reservation.getReservationTime();

            if (reservationTime.isBefore(currentTime.plusHours(3))) {
                throw new IllegalStateException("Reservations cannot be canceled within 3 hours of the reservation time.");
            }

        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return reservationMapper.getDTOFromDomain(reservation);
    }

}
