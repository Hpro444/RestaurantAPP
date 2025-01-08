package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.TableDTO;

import java.util.List;

public interface TableService {
    List<TableDTO> getTablesForRestaurant(Long restaurantId);
}
