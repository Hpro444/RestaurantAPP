package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    Optional<List<Benefit>> getBenefitsByRestaurantId(Long restaurantId);

    Optional<List<Benefit>> getBenefitsByRestaurantIdAndNumberOfNeededReservations(Long restaurantId, Integer number_of_needed_reservations);
}
