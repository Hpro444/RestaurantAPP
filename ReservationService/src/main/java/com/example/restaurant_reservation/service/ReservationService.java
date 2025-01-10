package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.ReservationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    Long makeReservationForCustomer(Long customerId, Long tableEntityId, Long appointmentId, String description);
    void cancelReservationForCustomer(Long reservationId);
    ReservationDTO getReservationById(Long id);
    List<ReservationDTO> getReservationsByCustomer(Long customerId);

    // Fetch reservations for a specific restaurant within a date range
    List<ReservationDTO> getReservationsForRestaurantByDateRange(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate);
    List<ReservationDTO> getReservationsForRestaurantToday(Long restaurantId);

    String getManagerEmailByReservationId(Long id);
    String getRestaurantNameByReservationId(Long id);

// Required by project specification
//    void notifyReservationSuccess(Long reservationId);
//    void notifyReservationCancellation(Long reservationId);
//    void defineLoyaltyBenefit(Long restaurantId, int requiredReservations, String benefitDescription);
//    String getLoyaltyBenefitForCustomer(Long restaurantId, Long customerId);


}
