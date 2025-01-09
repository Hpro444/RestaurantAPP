package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.TableEntity;
import com.example.restaurant_reservation.dto.TableDTO;
import com.example.restaurant_reservation.mapper.TableMapper;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.service.TableService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.domain.AppointmentEntity;

import java.util.Optional;
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

    @Override
    public List<AppointmentDTO> getAppointmentForTable(Long tableId) {
        Optional<TableEntity> optionalTable = tableRepository.findById(tableId);

        if (optionalTable.isPresent()) {
            TableEntity table = optionalTable.get();

            List<AppointmentEntity> appointments = table.getAppointments();

            return appointments.stream()
                    .map(appointment -> new AppointmentDTO(
                            appointment.getDate(),
                            appointment.isAvailable()
                    ))
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Table with ID " + tableId + " not found.");
        }
    }

}
