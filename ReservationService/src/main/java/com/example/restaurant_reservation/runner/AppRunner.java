package com.example.restaurant_reservation.runner;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.repository.AppointmentRepository;
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

    @Bean
    CommandLineRunner loadData() {
        return args -> {
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

            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);

            TableEntity table1 = new TableEntity(10, true, "Indoor", "Table 1", 1L);
            tableRepository.save(table1);
            TableEntity table2 = new TableEntity(10, true, "Indoor", "Table 2", 1L);
            tableRepository.save(table2);
            TableEntity table3 = new TableEntity(10, true, "Indoor", "Table 3", 1L);
            tableRepository.save(table3);
            TableEntity table4 = new TableEntity(10, true, "Indoor", "Table 4", 1L);
            tableRepository.save(table4);


            AppointmentEntity appointment1 = new AppointmentEntity();
            appointment1.setDate(LocalDateTime.now().plusDays(1));
            appointment1.setAvailable(true);
            appointment1.setTable(table1);

            AppointmentEntity appointment2 = new AppointmentEntity();
            appointment2.setDate(LocalDateTime.now().plusDays(7));
            appointment2.setAvailable(true);
            appointment2.setTable(table2);

            appointmentRepository.save(appointment1);
            appointmentRepository.save(appointment2);
        };
    }
}
