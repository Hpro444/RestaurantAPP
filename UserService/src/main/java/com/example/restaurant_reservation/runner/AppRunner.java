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
            User user = new User("Mateja", "Subin", "subin", "password", "subin.mateja@gmail.com", date, false, "", new Address(), Role.ADMIN);
            userRepository.save(user);
        };
    }
}
