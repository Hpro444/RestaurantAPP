package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.PersonDTO;

public interface UserService {

    /// register();
    String login (LoginDTO loginDTO);
    void register(PersonDTO personDTO);
}
