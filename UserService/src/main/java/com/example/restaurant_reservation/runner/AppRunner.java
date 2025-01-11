package com.example.restaurant_reservation.runner;

import com.example.restaurant_reservation.domain.Address;
import com.example.restaurant_reservation.domain.Role;
import com.example.restaurant_reservation.domain.User;
import com.example.restaurant_reservation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;

@AllArgsConstructor
@Configuration
public class AppRunner {
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            Date date = new Date(System.currentTimeMillis());
            Address address = new Address();
            address.setCity("Berlin");
            address.setZip("12345");
            address.setStreet("Main Street");
            address.setState("sumadija");

            User user = new User("Mateja", "Subin", "subin", "password", "subin.mateja@gmail.com", date, false, "", address, Role.ADMIN);
            userRepository.save(user);

            User user2 = new User("Andrija", "Mililililikic", "andrijac", "password", "andrija@gmail.com", date, false, "", address, Role.CUSTOMER);
            userRepository.save(user2);

            User user3 = new User("Andrija Menadzer", "prezime", "andrijam", "password", "menadzer@gmail.com", date, false, "", address, Role.MANAGER);
            userRepository.save(user3);

        };
    }
}
