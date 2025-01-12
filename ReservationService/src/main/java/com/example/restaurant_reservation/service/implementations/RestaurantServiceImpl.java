package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.BenefitDTO;
import com.example.restaurant_reservation.dto.FilterDTO;
import com.example.restaurant_reservation.dto.RestaurantDTO;
import com.example.restaurant_reservation.mapper.AddressMapper;
import com.example.restaurant_reservation.mapper.AppointmentMapper;
import com.example.restaurant_reservation.mapper.BenefitMapper;
import com.example.restaurant_reservation.mapper.RestaurantMapper;
import com.example.restaurant_reservation.repository.AppointmentRepository;
import com.example.restaurant_reservation.repository.BenefitRepository;
import com.example.restaurant_reservation.repository.RestaurantRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final TableRepository tableRepository;
    private final AppointmentRepository appointmentRepository;

    private final RestaurantMapper restaurantMapper;
    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;
    private final AddressMapper addressMapper;
    private final AppointmentMapper appointmentMapper;


    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with ID " + id + " not found."));
        return restaurantMapper.getDTOFromDomain(restaurant);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::getDTOFromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantMapper.getDomainFromDTO(restaurantDTO);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with ID " + id + " not found."));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setKitchenType(restaurantDTO.getKitchenType());
        restaurant.setManagerId(restaurant.getManagerId());
        restaurant.setManagerEmail(restaurant.getManagerEmail());
        restaurant.setOpeningTime(restaurantDTO.getOpeningTime());
        restaurant.setClosingTime(restaurantDTO.getClosingTime());

        restaurantRepository.save(restaurant);

    }

    @Override
    public List<BenefitDTO> getAllBenefitsForRestaurant(Long restaurantId) {
        List<Benefit> benefits = benefitRepository.getBenefitsByRestaurantId(restaurantId).orElseThrow(() -> new EntityNotFoundException("Benefit with ID " + restaurantId + " not found."));
        return benefits.stream().map(benefitMapper::getDTOFromDomain).toList();
    }

    @Override
    public void addBenefitToRestaurant(long id, BenefitDTO benefitDTO) {
        Benefit benefit = benefitMapper.getDomainFromDTO(benefitDTO);
        benefitRepository.save(benefit);
    }

    @Override
    public void removeBenefitFromRestaurant(long benefitId) {
        benefitRepository.deleteById(benefitId);
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentsByFilter(FilterDTO filterDTO) {

        // Retrieve all restaurants from the database
        List<Restaurant> restaurants = restaurantRepository.findAll();

        // Filter restaurants by kitchen type if specified in the filter DTO
        if (filterDTO.getKitchenType() != null) {
            restaurants = restaurants.stream()
                    .filter(restaurant -> restaurant.getKitchenType().equals(KitchenType.valueOf(filterDTO.getKitchenType())))
                    .toList();
        }

        // Filter restaurants by address if specified in the filter DTO
        if (filterDTO.getAddress() != null) {
            restaurants = restaurants.stream()
                    .filter(restaurant -> restaurant.getAddress().equals(addressMapper.getDomainFromDTO(filterDTO.getAddress())))
                    .toList();
        }

        // Get all tables for each restaurant and flatten into a single list
        List<TableEntity> tableEntities = restaurants.stream()
                .flatMap(restaurant -> tableRepository.findAllByRestaurantId(restaurant.getId()).stream())
                .toList();

        // Filter tables by capacity if specified in the filter DTO
        if (filterDTO.getCapacity() != 0) {
            tableEntities = tableEntities.stream()
                    .filter(table -> table.getCapacity() == filterDTO.getCapacity())
                    .toList();
        }

        // Get all appointments for each table and flatten into a single list
        List<AppointmentEntity> appointmentEntityList = tableEntities.stream()
                .flatMap(tableEntity -> appointmentRepository.findByTable(tableEntity).orElse(Collections.emptyList()).stream().filter(AppointmentEntity::isAvailable))
                .toList();

        // Filter appointments by date if specified in the filter DTO
        if (filterDTO.getDate() != null) {
            appointmentEntityList = appointmentEntityList.stream()
                    .filter(appointment -> appointment.getDate().toLocalDate().equals(filterDTO.getDate()))
                    .toList();
        }

        // Filter appointments by start and end time if specified in the filter DTO
        if (filterDTO.getStartTime() != null && filterDTO.getEndTime() != null) {
            appointmentEntityList = appointmentEntityList.stream()
                    .filter(appointment -> appointment.getDate().toLocalTime().isAfter(filterDTO.getStartTime()) && appointment.getDate().toLocalTime().isBefore(filterDTO.getEndTime()))
                    .toList();
        }

        if (filterDTO.getStartTime() != null && filterDTO.getEndTime() == null) {
            appointmentEntityList = appointmentEntityList.stream()
                    .filter(appointment -> appointment.getDate().toLocalTime().equals(filterDTO.getStartTime()))
                    .toList();
        }

        if (filterDTO.getStartTime() == null && filterDTO.getEndTime() != null) {
            appointmentEntityList = appointmentEntityList.stream()
                    .filter(appointment -> appointment.getDate().toLocalTime().isBefore(filterDTO.getEndTime()))
                    .toList();
        }


        // Map appointment entities to DTOs and return as a list
        return appointmentEntityList.stream()
                .map(appointmentMapper::getDTOFromDomain)
                .toList();
    }


    @Override
    public RestaurantDTO getRestaurantByTableId(Long tableId) {
        TableEntity tableEntity = tableRepository.findById(tableId).orElseThrow(() -> new EntityNotFoundException("Table with ID " + tableId + " not found."));
        Restaurant restaurant = restaurantRepository.findById(tableEntity.getId()).orElseThrow(() -> new EntityNotFoundException("Restaurant with ID " + tableId + " not found."));
        return restaurantMapper.getDTOFromDomain(restaurant);
    }


//    @Override
//    public List<RestaurantDTO> getAllRestaurantsByManagerId(Long managerId) {
//        return restaurantRepository.findAllByManagerId(managerId).orElseThrow(RuntimeException::new).stream()
//                .map(restaurantMapper::getDTOFromDomain)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<RestaurantDTO> getAllRestaurantsByManager(Long managerId) {
        List<Restaurant> restaurants = restaurantRepository.findByManagerId(managerId);

//        if (restaurants.isEmpty()) {
//            throw new RuntimeException("No restaurants found for this manager.");
//        }

        return restaurants.stream()
                .map(restaurantMapper::getDTOFromDomain) // Assuming you have a mapper for entity-to-DTO conversion
                .collect(Collectors.toList());
    }

}
