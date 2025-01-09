package com.example.restaurant_reservation.runner;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.repository.CustomerRepository;
import com.example.restaurant_reservation.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalTime;

@AllArgsConstructor
@Configuration
public class AppRunner {
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            Address address = new Address();
            Customer customer = new Customer("Andrija", "Mililililikic", "andrijac", "password", "andrija@gmail.com", new Date(System.currentTimeMillis()), address, Status.ACTIVE, Role.CUSTOMER, 5);
            customerRepository.save(customer);

            Manager manager = new Manager();
            managerRepository.save(manager);

        };
    }
}
