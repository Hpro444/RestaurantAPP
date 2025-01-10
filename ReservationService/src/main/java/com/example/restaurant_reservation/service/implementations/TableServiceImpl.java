package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.AppointmentEntity;
import com.example.restaurant_reservation.domain.TableEntity;
import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.TableDTO;
import com.example.restaurant_reservation.mapper.AppointmentMapper;
import com.example.restaurant_reservation.mapper.TableMapper;
import com.example.restaurant_reservation.repository.AppointmentRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.service.TableService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {

    private TableRepository tableRepository;
    private TableMapper tableMapper;
    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;

    @Override
    public List<TableDTO> getTablesForRestaurant(Long restaurantId) {
        return tableRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(table -> tableMapper.getDTOFromDomain(table))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentForTable(Long tableId) {
        TableEntity tableEntity = tableRepository.findById(tableId).orElseThrow(EntityNotFoundException::new);
        Optional<List<AppointmentEntity>> appointmentEntity = appointmentRepository.findByTable(tableEntity);

        if (appointmentEntity.isPresent()) {

            List<AppointmentEntity> appointments = appointmentEntity.get();

            return appointments.stream().filter(AppointmentEntity::isAvailable)
                    .map(appointment -> appointmentMapper.getDTOFromDomain(appointment))
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Table with ID " + tableId + " not found.");
        }
    }


    @Override
    public AppointmentEntity getAppointmentByLocalDateTime(Long tableId, LocalDateTime localDateTime) {
        TableEntity tableEntity = tableRepository.findById(tableId).orElseThrow(EntityNotFoundException::new);
        Optional<List<AppointmentEntity>> appointmentEntity = appointmentRepository.findByTable(tableEntity);
        if (appointmentEntity.isPresent()) {
            List<AppointmentEntity> appointments = appointmentEntity.get();
            return appointments.stream()
                    .filter(appointment -> appointment.getDate().equals(localDateTime))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void addTableToRestaurant(TableDTO tableDTO) {
        TableEntity table = tableMapper.getDomainFromDTO(tableDTO);
        tableRepository.save(table);

    }

    @Override
    public List<TableDTO> getAllTablesByRestaurant(Long restaurantId) {
        // Fetch all tables where the restaurantId matches
        return tableRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(tableMapper::getDTOFromDomain) // Map each TableEntity to TableDTO
                .collect(Collectors.toList());
    }

    @Override
    public TableDTO updateTable(Long tableId, TableDTO updatedTableDTO) {
        TableEntity table = tableRepository.findById(tableId).orElseThrow(EntityNotFoundException::new);

        table.setTableName(updatedTableDTO.getTableName());
        table.setZone(updatedTableDTO.getZone());
        table.setCapacity(updatedTableDTO.getCapacity());
        table.setMergeable(updatedTableDTO.isMergeable());
        table = tableRepository.save(table);

        return tableMapper.getDTOFromDomain(table);
    }

    @Override
    public void deleteTable(Long tableId) {
        TableEntity table = tableRepository.findById(tableId).orElseThrow(EntityNotFoundException::new);
        tableRepository.delete(table);
    }

    @Override
    public List<TableDTO> getAvailableTables(Long restaurantId, String zone, int capacity, LocalDateTime dateTime) {
        List<TableEntity> tableEntities = tableRepository.findAllByRestaurantId(restaurantId);

        List<TableEntity> filteredTables = tableEntities.stream()
                .filter(table -> table.getZone().equalsIgnoreCase(zone))
                .filter(table -> table.getCapacity() >= capacity)
//                .filter(table -> {
//                    // Check if the table is available at the given dateTime
//                    return table.getAppointments().stream()
//                            .anyMatch(appointment -> appointment.getLocalDateTime().equals(dateTime) && appointment.isAvailable());
//                })
                .toList();

        return filteredTables.stream()
                .map(tableMapper::getDTOFromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void addAppointmentToTable(AppointmentDTO appointmentDTO) {
        AppointmentEntity appointmentEntity = appointmentMapper.getDomainFromDTO(appointmentDTO);
        appointmentRepository.save(appointmentEntity);
    }


}
