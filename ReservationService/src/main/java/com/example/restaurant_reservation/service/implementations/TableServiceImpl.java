package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.dto.TableDTO;
import com.example.restaurant_reservation.mapper.TableMapper;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {

    private TableRepository tableRepository;
    private TableMapper tableMapper;

    @Override
    public List<TableDTO> getTablesForRestaurant(Long restaurantId) {
        return tableRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(table -> tableMapper.getDTOFromDomain(table))
                .collect(Collectors.toList());
    }

}
