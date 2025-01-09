package com.example.restaurant_reservation.service;

import java.io.IOException;

public interface UserService {
     String getEmailForUser(Long userId)throws IOException, InterruptedException;
}
