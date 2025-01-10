package com.example.restaurant_reservation.controller;

import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.UserDTO;
import com.example.restaurant_reservation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }
    @GetMapping("/get_email/{user_id}")
    public ResponseEntity<String> getEmail(@PathVariable Long user_id){
        return new ResponseEntity<>(userService.getEmail(user_id), HttpStatus.OK);
    }


}
