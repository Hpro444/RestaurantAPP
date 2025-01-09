package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
    List<TableEntity> findAllByRestaurantId(Long restaurantId);
}
