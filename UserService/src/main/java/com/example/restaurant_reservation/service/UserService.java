package com.example.restaurant_reservation.service;

import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.ManagerDTO;
import com.example.restaurant_reservation.dto.UserDTO;

public interface UserService {

    /// register();
    String login(LoginDTO loginDTO);

    String registerCostumer(CustomerDTO customerDTO);

    String registerManager(ManagerDTO managerDTO);

    String getEmail(Long id);

    String activateUser(Long id, String code);

    UserDTO findUserById(Long id);

    void banUser(String userName);

    void unBanUser(String userName);
}
