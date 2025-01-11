package com.example.restaurant_reservation;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

}
