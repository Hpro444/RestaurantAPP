package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.TableDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@AllArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping("/restaurant/{restaurantId}")
    @CheckSecurity
    public ResponseEntity<List<TableDTO>> getTablesForRestaurant(@RequestHeader("Authorization") String authorization, @PathVariable Long restaurantId) {
        List<TableDTO> tables = tableService.getTablesForRestaurant(restaurantId);
        if (tables.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/appointment/{tableId}")
    @CheckSecurity
    public ResponseEntity<List<AppointmentDTO>> getAppointmentForTable(@RequestHeader("Authorization") String authorization, @PathVariable Long tableId) {
        List<AppointmentDTO> appointment = tableService.getAppointmentForTable(tableId);
        if (appointment == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointment);
    }


    @PostMapping
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> createTable(@RequestHeader("Authorization") String authorization, @RequestBody TableDTO tableDTO) {
        try {
            tableService.addTableToRestaurant(tableDTO);
            return ResponseEntity.ok("Table created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Table creation failed");
        }
    }

    @PostMapping("/appointment")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> addAppointmentToTable(@RequestHeader("Authorization") String authorization, @RequestBody AppointmentDTO appointmentDTO) {
        try {
            tableService.addAppointmentToTable(appointmentDTO);
            return ResponseEntity.ok("Appointment added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

