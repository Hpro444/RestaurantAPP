package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.TableDTO;
import com.example.restaurant_reservation.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tables")
@AllArgsConstructor
public class TableController {
    private final TableService tableService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<TableDTO>> getTablesForRestaurant(@PathVariable Long restaurantId) {
        List<TableDTO> tables = tableService.getTablesForRestaurant(restaurantId);
        if (tables.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tables);
    }
}

