package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.User;
import com.example.restaurant_reservation.domain.Role;
import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.UserDTO;
import com.example.restaurant_reservation.repository.UserRepository;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Transactional  // Ensures all database operations within this service class are wrapped in a transaction. If any operation fails, changes are ROLLED back.
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TokenService tokenService;

    @Override
    public String login(LoginDTO loginDto) {
        User user = userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElse(null);
        if(user != null) {
            Claims claims = Jwts.claims();
            claims.put("role", user.getRole());
            claims.put("username", user.getUsername());
            claims.put("email", user.getEmail());
            claims.setExpiration(new Date(System.currentTimeMillis() + 3600000)); // 1 hour
            return tokenService.generate(claims);
        }
        else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public void register(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Encode password
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(Role.valueOf(userDTO.getRole()));
        user.setBirthDate(userDTO.getBirthDate());

        userRepository.save(user);
    }

    @Override
    public String getEmail(Long id) {
        return userRepository.findById(id).map(User::getEmail).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
