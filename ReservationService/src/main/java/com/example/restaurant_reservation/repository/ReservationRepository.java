package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByCustomerId(Long customerId);

    // Fetch reservations for a restaurant within a date range
    @Query("""
        SELECT r FROM Reservation r
        JOIN r.table t
        WHERE t.restaurantId = :restaurantId
        AND r.reservationTime BETWEEN :startDate AND :endDate
    """)
    List<Reservation> findReservationsByRestaurantIdAndDateRange(
            @Param("restaurantId") Long restaurantId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Fetch all reservations for a specific restaurant
    @Query("""
        SELECT r FROM Reservation r
        JOIN r.table t
        WHERE t.restaurantId = :restaurantId
    """)
    List<Reservation> findReservationsByRestaurantId(@Param("restaurantId") Long restaurantId);
}