package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
    List<TableEntity> findAllByRestaurantId(Long restaurantId);

    @Query("SELECT t.restaurantId FROM TableEntity t WHERE t.id = :tableId")
    Long findRestaurantIdByTableId(@Param("tableId") Long tableId);
}
