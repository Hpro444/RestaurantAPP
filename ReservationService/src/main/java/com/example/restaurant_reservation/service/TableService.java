package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.domain.AppointmentEntity;
import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.TableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TableService {
    List<TableDTO> getTablesForRestaurant(Long restaurantId);

    List<AppointmentDTO> getAppointmentForTable(Long tableId);


    AppointmentEntity getAppointmentByLocalDateTime(Long tableId, LocalDateTime localDateTime);

    void addTableToRestaurant(TableDTO tableDTO);

    List<TableDTO> getAllTablesByRestaurant(Long restaurantId);

    TableDTO updateTable(Long tableId, TableDTO updatedTableDTO);

    void deleteTable(Long tableId);     // if managers want to delete a table

    List<TableDTO> getAvailableTables(Long restaurantId, String zone, int capacity, LocalDateTime dateTime);

    void addAppointmentToTable(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointmentById(Long appointmentId);

}
