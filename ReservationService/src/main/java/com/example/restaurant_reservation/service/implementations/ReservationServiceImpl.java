package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.AppointmentEntity;
import com.example.restaurant_reservation.domain.Reservation;
import com.example.restaurant_reservation.domain.Restaurant;
import com.example.restaurant_reservation.domain.TableEntity;
import com.example.restaurant_reservation.dto.AppointmentDTO;
import com.example.restaurant_reservation.dto.ReservationDTO;
import com.example.restaurant_reservation.mapper.ReservationMapper;
import com.example.restaurant_reservation.repository.AppointmentRepository;
import com.example.restaurant_reservation.repository.ReservationRepository;
import com.example.restaurant_reservation.repository.RestaurantRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import com.example.restaurant_reservation.service.ReservationService;
import com.example.restaurant_reservation.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private TableRepository tableRepository;
    private RestaurantRepository restaurantRepository;
    private TableService tableService;
    private AppointmentRepository appointmentRepository;

    private ReservationMapper reservationMapper;


    @Override
    public Long makeReservationForCustomer(Long customerId, Long tableEntityId, Long appointmentId, String description) {
        Optional<TableEntity> tableEntity = tableRepository.findById(tableEntityId);
        if (tableEntity.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setCustomerId(customerId);
            reservation.setTable(tableEntity.get());
            AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId).orElseThrow(RuntimeException::new);
            reservation.setAppointment(appointmentEntity);
            reservation.setDescription(description);
            AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow(RuntimeException::new);
            appointment.setAvailable(false);

            reservationRepository.save(reservation);

            return reservation.getId();
        }

        return null;
    }

    @Override
    public void cancelReservationForCustomer(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));


        LocalDateTime currentTime = LocalDateTime.now();
        AppointmentEntity appointment = reservation.getAppointment();

        LocalDateTime reservationTime = appointment.getDate();
        if (reservationTime.isBefore(currentTime.plusHours(3))) {
            throw new IllegalStateException("Reservations cannot be canceled within 3 hours of the reservation time.");
        }
        appointment.setAvailable(true);

        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return reservationMapper.getDTOFromDomain(reservation);
    }

    @Override
    public String getManagerEmailByReservationId(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Restaurant restaurant = restaurantRepository.findById(reservation.getTable().getRestaurantId()).orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getManagerEmail();
    }

    @Override
    public String getRestaurantNameByReservationId(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Restaurant restaurant = restaurantRepository.findById(reservation.getTable().getRestaurantId()).orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getName();
    }

    @Override
    public List<ReservationDTO> getReservationsForRestaurantByDateRange(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate) {
        return reservationRepository.findReservationsByRestaurantIdAndDateRange(restaurantId, startDate, endDate)
                .stream()
                .map(reservationMapper::getDTOFromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsForRestaurantToday(Long restaurantId) {
        LocalDateTime today = LocalDateTime.now();
        return reservationRepository.findReservationsByRestaurantIdAndDateRange(restaurantId, today, today)
                .stream()
                .map(reservationMapper::getDTOFromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByCustomer(Long customerId) {
        return reservationRepository.findReservationsByCustomerId(customerId)
                .stream()
                .map(reservationMapper::getDTOFromDomain)
                .collect(Collectors.toList());
    }

}
