package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.AppointmentEntity;
import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.repository.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppointmentMapper {
    private final TableRepository tableRepository;

    public AppointmentDTO getDTOFromDomain(AppointmentEntity appointmentEntity) {
        AppointmentDTO dto = new AppointmentDTO();
//        dto.setId(appointmentEntity.getId());
        dto.setAvailable(appointmentEntity.isAvailable());
        dto.setDate(appointmentEntity.getDate());
        dto.setTableId(appointmentEntity.getTable().getId());
        return dto;
    }

    public AppointmentEntity getDomainFromDTO(AppointmentDTO dto) {
        AppointmentEntity entity = new AppointmentEntity();
//        entity.setId(dto.getId());
        entity.setAvailable(dto.isAvailable());
        entity.setDate(dto.getDate());
        entity.setTable(tableRepository.findById(dto.getTableId()).orElseThrow(() -> new RuntimeException("Table not found")));
        return entity;
    }
}
