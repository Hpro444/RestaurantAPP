package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.CustomerDTO;
import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.ManagerDTO;
import com.example.restaurant_reservation.dto.UserDTO;
import com.example.restaurant_reservation.security.CheckSecurity;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/auth/register/customer")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            String token = userService.registerCostumer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/auth/register/manager")
    public ResponseEntity<String> registerManager(@RequestBody ManagerDTO managerDTO) {
        try {
            String token = userService.registerManager(managerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }

    @GetMapping("/get_email/{user_id}")
    public ResponseEntity<String> getEmail(@PathVariable Long user_id) {
        return new ResponseEntity<>(userService.getEmail(user_id), HttpStatus.OK);
    }

    @PostMapping("/register/activation")
    @CheckSecurity
    public ResponseEntity<String> activateUser(@RequestHeader("Authorization") String authorization, @RequestBody String code) {
        try {
            authorization = authorization.replace("Bearer ", "");
            Claims claims = tokenService.parseToken(authorization);
            Long user_id = claims.get("user_id", Long.class);
            if (user_id == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");

            String message = userService.activateUser(user_id, code);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    @CheckSecurity
    public ResponseEntity<UserDTO> getCustomerById(@RequestHeader("Authorization") String authorization, @PathVariable Long user_id) {
        try {
            UserDTO customer = userService.findUserById(user_id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Use status(500) for internal server errors
        }
    }

    @PostMapping("/ban")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> banUser(@RequestHeader("Authorization") String authorization, @RequestBody String userName) {
        try {
            userService.banUser(userName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/unban")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<String> unBanUser(@RequestHeader("Authorization") String authorization, @RequestBody String userName) {
        try {
            userService.unBanUser(userName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
