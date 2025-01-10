package com.example.restaurant_reservation.mapper;

import com.example.restaurant_reservation.domain.TableEntity;
import com.example.restaurant_reservation.dto.TableDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TableMapper {

    public TableEntity getDomainFromDTO(TableDTO tableDTO) {
        TableEntity tableEntity = new TableEntity();
        tableEntity.setCapacity(tableDTO.getCapacity());
        tableEntity.setZone(tableDTO.getZone());
        tableEntity.setTableName(tableDTO.getTableName());
        tableEntity.setMergeable(tableDTO.isMergeable());
        tableEntity.setRestaurantId(tableDTO.getRestaurantId());

        return tableEntity;
    }

    public TableDTO getDTOFromDomain(TableEntity table) {
        TableDTO tableDTO = new TableDTO();
        tableDTO.setTableName(table.getTableName());
        tableDTO.setZone(table.getZone());
        tableDTO.setCapacity(table.getCapacity());
        tableDTO.setMergeable(table.isMergeable());
        tableDTO.setRestaurantId(table.getRestaurantId());
        return tableDTO;
    }
}
