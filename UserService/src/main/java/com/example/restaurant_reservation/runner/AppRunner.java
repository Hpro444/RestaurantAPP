package com.example.restaurant_reservation.runner;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.repository.AdminRepository;
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
    private final AdminRepository adminRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            Address address = new Address();
            Customer customer = new Customer("Andrija", "Mililililikic", "andrijac", "password", "andrija@gmail.com", new Date(System.currentTimeMillis()), address, Status.ACTIVE, Role.CUSTOMER, 5);
            customerRepository.save(customer);

            Manager manager = new Manager();
            managerRepository.save(manager);

            Date date = new Date(System.currentTimeMillis());
            Manager manager2 = new Manager(date, "Andrija Menadzer", "prezime", "andrijam", "password", "menadzer@gmail.com", new Date(System.currentTimeMillis()), address, Status.ACTIVE, Role.MANAGER);
            managerRepository.save(manager2);

            Manager manager3 = new Manager();
            manager3.setFirstName("Andrija");
            manager3.setLastName("Milikic");
            manager3.setEmail("andrija333@gmail.com");
            manager3.setUsername("a3");
            manager3.setPassword("password");
            manager3.setRole(Role.MANAGER);
            manager3.setStatus(Status.ACTIVE);
            manager3.setAddress(address);
            managerRepository.save(manager3);

            Admin admin = new Admin();
            admin.setFirstName("Andrija admin");
            admin.setLastName("Milikic admin");
            admin.setEmail("andrijaadmin@gmail.com");
            admin.setUsername("admin");
            admin.setPassword("password");
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.ACTIVE);
            admin.setAddress(address);
            adminRepository.save(admin);
        };
    }
}
