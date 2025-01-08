package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsernameAndPassword(String username, String password);   // may or may not contain a value (null check in that orElseNull lambda)
    Boolean existsByUsername(String username);
}
