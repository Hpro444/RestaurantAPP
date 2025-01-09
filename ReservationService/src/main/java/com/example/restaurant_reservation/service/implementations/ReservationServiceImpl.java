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

    private TokenService tokenService;

//    @Override
//    public ReservationDTO makeReservationForCustomer(Long customerId, Long tableEntityId, String reservationDate) {
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//        TableEntity tableEntity = tableRepository.findById(tableEntityId)
//                .orElseThrow(() -> new RuntimeException("Table not found"));
//
//        LocalDateTime reservationTime;
//        try {
//            reservationTime = LocalDateTime.parse(reservationDate);
//        } catch (DateTimeParseException e) {
//            throw new IllegalArgumentException("Invalid date-time format. Please use ISO 8601 format, e.g., 2025-01-02T14:30:00.");
//        }
//
//        Reservation reservation = new Reservation();
//        reservation.setCustomer(customer);
//        reservation.setTable(tableEntity);
//        reservation.setReservationTime(reservationTime);
//
//        customer.getReservations().add(reservation);
//        reservationRepository.save(reservation);
//
//        return reservationMapper.getDTOFromDomain(reservation);
//    }

    // TODO: ...
    @Override
    public ReservationDTO makeReservationForCustomer(Long customerId, Long tableEntityId, String reservationDate) {
        return null;
    }

    @Override
    public void cancelReservationForCustomer(Long reservationId, String token) {
        String role = getRoleFromToken(token);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!"ROLE_MANAGER".equals(role)) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime reservationTime = reservation.getReservationTime();

            if (reservationTime.isBefore(currentTime.plusHours(3))) {
                throw new IllegalStateException("Reservations cannot be canceled within 3 hours of the reservation time.");
            }
        }

        reservationRepository.delete(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    private String getRoleFromToken(String token) {
        Claims claims = tokenService.parseToken(token);
        if (claims == null) {
            throw new RuntimeException("Unauthorized: Invalid token");
        }
        return claims.get("role", String.class);
    }
}
