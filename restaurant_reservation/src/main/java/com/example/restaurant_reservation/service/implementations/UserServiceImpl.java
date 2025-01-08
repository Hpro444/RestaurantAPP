package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.domain.Person;
import com.example.restaurant_reservation.domain.Role;
import com.example.restaurant_reservation.dto.LoginDTO;
import com.example.restaurant_reservation.dto.PersonDTO;
import com.example.restaurant_reservation.repository.PersonRepository;
import com.example.restaurant_reservation.security.service.TokenService;
import com.example.restaurant_reservation.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional  // Ensures all database operations within this service class are wrapped in a transaction. If any operation fails, changes are ROLLED back.
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private PersonRepository personRepository;
    private TokenService tokenService;

    @Override
    public String login(LoginDTO loginDto) {
        Person person = personRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElse(null);
        if(person != null){
            Claims claims = Jwts.claims();
            claims.put("role", person.getRole());
            claims.put("username", person.getUsername());
            return tokenService.generate(claims);
        }
        return "User does not exist";
    }

    @Override
    public void register(PersonDTO personDTO) {
        if (personRepository.existsByUsername(personDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Person person = new Person();
        person.setUsername(personDTO.getUsername());
        person.setPassword(personDTO.getPassword()); // Encode password
        person.setEmail(personDTO.getEmail());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setRole(Role.valueOf(personDTO.getRole()));
        person.setBirthDate(personDTO.getBirthDate());

        personRepository.save(person);
    }
}
