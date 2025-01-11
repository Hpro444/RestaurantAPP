package com.example.restaurant_reservation.runner;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.repository.AppointmentRepository;
import com.example.restaurant_reservation.repository.ReservationRepository;
import com.example.restaurant_reservation.repository.RestaurantRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Configuration
public class AppRunner {

    private final RestaurantRepository restaurantRepository;
    private final TableRepository tableRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReservationRepository reservationRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            // Adding Restaurants
            Restaurant restaurant1 = new Restaurant(
                    1L,
                    "subin.mateja@gmail.com",
                    "Bistro Bliss",
                    "Trendy French cuisine with a cozy atmosphere.",
                    LocalTime.of(12, 0),
                    LocalTime.of(22, 0),
                    KitchenType.ITALIAN,
                    new Address("123 Main St", "Anytown", "USA", "231")
            );

            Restaurant restaurant2 = new Restaurant(
                    2L,
                    "msubin8323rn@raf.rs",
                    "Tasty Bites",
                    "Delicious Asian fusion with a modern twist.",
                    LocalTime.of(11, 30),
                    LocalTime.of(21, 30),
                    KitchenType.SERBIAN,
                    new Address("456 Elm St", "Othertown", "USA", "123")
            );

            Restaurant restaurant3 = new Restaurant(
                    3L,
                    "manager3@example.com",
                    "Sushi Corner",
                    "Authentic Japanese sushi with a contemporary vibe.",
                    LocalTime.of(12, 0),
                    LocalTime.of(23, 0),
                    KitchenType.ITALIAN,
                    new Address("789 Oak St", "Springfield", "USA", "456")
            );

            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);
            restaurantRepository.save(restaurant3);

            // Adding Tables
            TableEntity table1 = new TableEntity(4, true, "Indoor", "Table 1", restaurant1.getId());
            TableEntity table2 = new TableEntity(6, true, "Outdoor", "Table 2", restaurant1.getId());
            TableEntity table3 = new TableEntity(8, true, "Indoor", "Table 3", restaurant2.getId());
            TableEntity table4 = new TableEntity(4, false, "Private Room", "Table 4", restaurant2.getId());
            TableEntity table5 = new TableEntity(2, false, "Indoor", "Table 5", restaurant3.getId());
            TableEntity table6 = new TableEntity(10, true, "VIP", "Table 6", restaurant3.getId());

            tableRepository.save(table1);
            tableRepository.save(table2);
            tableRepository.save(table3);
            tableRepository.save(table4);
            tableRepository.save(table5);
            tableRepository.save(table6);

            // Adding Appointments
            AppointmentEntity appointment1 = new AppointmentEntity();
            appointment1.setDate(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0));
            appointment1.setAvailable(true);
            appointment1.setTable(table1);

            AppointmentEntity appointment2 = new AppointmentEntity();
            appointment2.setDate(LocalDateTime.now().plusDays(2).withHour(20).withMinute(0));
            appointment2.setAvailable(true);
            appointment2.setTable(table2);

            AppointmentEntity appointment3 = new AppointmentEntity();
            appointment3.setDate(LocalDateTime.now().plusDays(3).withHour(19).withMinute(0));
            appointment3.setAvailable(true);
            appointment3.setTable(table3);

            AppointmentEntity appointment4 = new AppointmentEntity();
            appointment4.setDate(LocalDateTime.now().plusDays(4).withHour(18).withMinute(30));
            appointment4.setAvailable(true);
            appointment4.setTable(table4);

            appointmentRepository.save(appointment1);
            appointmentRepository.save(appointment2);
            appointmentRepository.save(appointment3);
            appointmentRepository.save(appointment4);

            // Adding Reservations
            Reservation reservation1 = new Reservation();
            reservation1.setCustomerId(2L);
            reservation1.setTable(table1);
            reservation1.setAppointment(appointment1);
            reservation1.setDescription("Birthday dinner for two");

            Reservation reservation2 = new Reservation();
            reservation2.setCustomerId(2L);
            reservation2.setTable(table3);
            reservation2.setAppointment(appointment3);
            reservation2.setDescription("Business lunch");

            Reservation reservation3 = new Reservation();
            reservation3.setCustomerId(3L);
            reservation3.setTable(table4);
            reservation3.setAppointment(appointment4);
            reservation3.setDescription("Family dinner");

            reservationRepository.save(reservation1);
            reservationRepository.save(reservation2);
            reservationRepository.save(reservation3);
        };
    }
}
