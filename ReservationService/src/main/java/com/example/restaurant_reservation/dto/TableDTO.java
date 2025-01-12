package com.example.restaurant_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {
    private Long id;
    private int capacity;
    private boolean isMergeable;
    private String zone;
    private String tableName;
    private Long restaurantId;
}
