package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Customer;
import com.example.restaurant_reservation.domain.Manager;
import com.example.restaurant_reservation.domain.Role;
import com.example.restaurant_reservation.domain.User;
import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.ManagerDTO;
import com.example.restaurant_reservation.mapper.AddressMapper;
import com.example.restaurant_reservation.repository.UserRepository;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.NotificationService;
import com.example.restaurant_reservation.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Transactional
// Ensures all database operations within this service class are wrapped in a transaction. If any operation fails, changes are ROLLED back.
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AddressMapper addressMapper;
    private final NotificationService notificationService;


    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    @Override
    public String login(LoginDTO loginDto) {

        User user = userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getOne_time_registration_code().isEmpty())
            throw new RuntimeException("Please activate your account first");

        Claims claims = Jwts.claims();
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("user_id", user.getId());

        return tokenService.generate(claims);
    }

    @Override
    public String registerCostumer(CustomerDTO customerDTO) {
        if (userRepository.existsByUsername(customerDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Customer user = new Customer();
        user.setUsername(customerDTO.getUsername());
        user.setPassword(customerDTO.getPassword()); // Encode password
        user.setEmail(customerDTO.getEmail());
        user.setFirstName(customerDTO.getFirstName());
        user.setLastName(customerDTO.getLastName());
        user.setRole(Role.CUSTOMER);
        user.setBirthDate(customerDTO.getBirthDate());
        user.setAddress(addressMapper.getDomainFromDTO(customerDTO.getAddress()));
        user.setOne_time_registration_code(generateRandomString(12));
        notificationService.sendRegistrationNotification(user.getFirstName(), user.getLastName(), user.getEmail(), user.getOne_time_registration_code());
        userRepository.save(user);

        Claims claims = Jwts.claims();
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("user_id", user.getId());

        return tokenService.generate(claims);

    }

    @Override
    public String registerManager(ManagerDTO managerDTO) {
        if (userRepository.existsByUsername(managerDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Manager user = new Manager();
        user.setUsername(managerDTO.getUsername());
        user.setPassword(managerDTO.getPassword()); // Encode password
        user.setEmail(managerDTO.getEmail());
        user.setFirstName(managerDTO.getFirstName());
        user.setLastName(managerDTO.getLastName());
        user.setRole(Role.MANAGER);
        user.setBirthDate(managerDTO.getBirthDate());
        user.setAddress(addressMapper.getDomainFromDTO(managerDTO.getAddress()));
        user.setDateOfStartWorking(managerDTO.getDateOfStartWorking());
        user.setOne_time_registration_code(generateRandomString(12));
        notificationService.sendRegistrationNotification(user.getFirstName(), user.getLastName(), user.getEmail(), user.getOne_time_registration_code());

        userRepository.save(user);

        Claims claims = Jwts.claims();
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("user_id", user.getId());

        return tokenService.generate(claims);
    }

    @Override
    public String getEmail(Long id) {
        return userRepository.findById(id).map(User::getEmail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public String activateUser(Long id, String code) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getOne_time_registration_code().equals(code))
            throw new RuntimeException("Invalid activation code");

        user.setOne_time_registration_code("");

        return "ACTIVATED";
    }


}
