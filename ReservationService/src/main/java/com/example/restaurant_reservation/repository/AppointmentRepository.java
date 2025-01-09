package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    Optional<List<AppointmentEntity>> findByTableID(Long tableID);


}
