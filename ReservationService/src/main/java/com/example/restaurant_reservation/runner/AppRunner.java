package com.example.restaurant_reservation.runner;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.repository.RestaurantRepository;
import com.example.restaurant_reservation.repository.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalTime;

@AllArgsConstructor
@Configuration
public class AppRunner {

    private final RestaurantRepository restaurantRepository;
    private final TableRepository tableRepository;

//    @Bean
//    CommandLineRunner loadData() {
//        return args -> {
//            Address address = new Address();
//
//            Restaurant restaurant = new Restaurant("Smash burgers", "Najjaci burgeri u kraju", LocalTime.of(8,0,0), LocalTime.of(22,0,0), KitchenType.SERBIAN, address);
//            restaurantRepository.save(restaurant);
//
//            TableEntity table1 = new TableEntity();
//            table1.setCapacity(4);
//            table1.setZone("indoor");
//            table1.setTableName("Table 1");
//            table1.setRestaurantId(restaurant.getId());
//            tableRepository.save(table1);
//
//            TableEntity table2 = new TableEntity();
//            table2.setCapacity(6);
//            table2.setZone("outdoor");
//            table2.setTableName("Table 2");
//            table2.setRestaurantId(restaurant.getId());
//            tableRepository.save(table2);
//        };
//    }
}
