package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // This will be automatically implemented by Spring Data JPA
    Optional<Restaurant> findById(Long id);

    Optional<List<Restaurant>> findAllByManagerId(Long managerId);
}
