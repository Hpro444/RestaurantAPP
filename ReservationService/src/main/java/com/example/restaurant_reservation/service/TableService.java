package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.dto.TableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TableService {
    List<TableDTO> getTablesForRestaurant(Long restaurantId);
    List<AppointmentDTO> getAppointmentForTable(Long tableId);

    TableDTO addTableToRestaurant(Long restaurantId, TableDTO tableDTO);
    List<TableDTO> getAllTablesByRestaurant(Long restaurantId);

    TableDTO updateTable(Long tableId, TableDTO updatedTableDTO);
    void deleteTable(Long tableId);     // if managers want to delete a table
    List<TableDTO> getAvailableTables(Long restaurantId, String zone, int capacity, LocalDateTime dateTime);

}
