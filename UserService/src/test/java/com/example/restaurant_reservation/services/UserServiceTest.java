package com.example.restaurant_reservation.services;

import com.example.restaurant_reservation.domain.*;
import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.repository.UserRepository;
import com.example.restaurant_reservation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void login_ShouldReturnToken_WhenValidCredentials() {
        User person = new Admin(
                "John", "Doe", "johndoe", "password", "hpro444@gmail.com",
                new Date(System.currentTimeMillis()), new Address(), Status.ACTIVE, Role.ADMIN, 5
        );
        userRepository.save(person);

        LoginDTO loginDTO = new LoginDTO("johndoe", "password");

        String token = userService.login(loginDTO);
        System.out.println(token);
        assertNotNull(token);
        assertTrue(token.length() > 0); // Validate token length
    }

    @Test
    void login_ShouldReturnErrorMessage_WhenInvalidCredentials() {
        User user = new Customer(
                "John", "Doe", "johndoe", "password", "john@example.com",
                new Date(System.currentTimeMillis()), new Address(), Status.ACTIVE, Role.CUSTOMER, 5
        );
        userRepository.save(user);

        LoginDTO loginDTO = new LoginDTO("janedoe", "wrongpassword");

        String result = userService.login(loginDTO);

        assertEquals("User does not exist", result);
    }
}
