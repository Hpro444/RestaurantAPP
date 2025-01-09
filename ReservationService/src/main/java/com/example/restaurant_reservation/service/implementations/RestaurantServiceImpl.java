package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Restaurant;
import com.example.restaurant_reservation.dto.RestaurantDTO;
import com.example.restaurant_reservation.mapper.ReservationMapper;
import com.example.restaurant_reservation.mapper.RestaurantMapper;
import com.example.restaurant_reservation.mapper.TableMapper;
import com.example.restaurant_reservation.repository.ReservationRepository;
import com.example.restaurant_reservation.repository.RestaurantRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    private final RestaurantMapper restaurantMapper;
    private final ReservationMapper reservationMapper;
    private final TableMapper tableMapper;


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
    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantMapper.getDomainFromDTO(restaurantDTO);

        restaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.getDTOFromDomain(restaurant);
    }

    @Override
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with ID " + id + " not found."));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setKitchenType(restaurantDTO.getKitchenType());
        restaurant.setManagerId(restaurant.getManagerId());
        restaurant.setManager_email(restaurant.getManager_email());
        restaurant.setOpeningTime(restaurantDTO.getOpeningTime());
        restaurant.setClosingTime(restaurantDTO.getClosingTime());

        restaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.getDTOFromDomain(restaurant);
    }

}
