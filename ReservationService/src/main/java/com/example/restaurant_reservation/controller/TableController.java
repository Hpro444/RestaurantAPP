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

    @GetMapping("/appointments/{tableId}")
    @CheckSecurity
    public ResponseEntity<List<AppointmentDTO>> getAppointmentForTable(@RequestHeader("Authorization") String authorization, @PathVariable Long tableId) {
        List<AppointmentDTO> appointment = tableService.getAppointmentForTable(tableId);
        if (appointment == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/appointment/{appointment_id}")
    @CheckSecurity
    public ResponseEntity<AppointmentDTO> getAppointmentById(@RequestHeader("Authorization") String authorization, @PathVariable Long appointment_id) {
        AppointmentDTO appointment = tableService.getAppointmentById(appointment_id);
        if (appointment == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointment);
    }

//    @PostMapping
//    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
//    public ResponseEntity<String> createTable(@RequestHeader("Authorization") String authorization, @RequestBody TableDTO tableDTO) {
//        try {
//            tableService.addTableToRestaurant(tableDTO);
//            return ResponseEntity.ok("Table created successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Table creation failed");
//        }
//    }

    @PostMapping("/restaurant/{restaurantId}")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> addTableToRestaurant(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long restaurantId,
            @RequestBody TableDTO tableDTO
    ) {
        try {
            tableDTO.setRestaurantId(restaurantId); // Set the restaurant ID in the DTO
            System.out.println("Trying to add table");
            tableService.addTableToRestaurant(tableDTO);
            System.out.println("Table added successfully");
            return ResponseEntity.ok("Table added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add table: " + e.getMessage());
        }
    }


    @PostMapping("/appointment")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<String> addAppointmentToTable(@RequestHeader("Authorization") String authorization, @RequestBody AppointmentDTO appointmentDTO) {
        try {
            System.out.println("Received AppointmentDTO: " + appointmentDTO);
            System.out.println(appointmentDTO.getTableId());
            System.out.println(appointmentDTO.getDate());
            System.out.println(appointmentDTO.isAvailable());
            System.out.println("Trying to add appointment to table............");
            tableService.addAppointmentToTable(appointmentDTO);
            System.out.println("Appointment added successfully !!!!");
            return ResponseEntity.ok("Appointment added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

