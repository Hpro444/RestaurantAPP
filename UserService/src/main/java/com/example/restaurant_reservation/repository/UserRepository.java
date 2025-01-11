package com.example.restaurant_reservation.repository;

import com.example.restaurant_reservation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);   // may or may not contain a value (null check in that orElseNull lambda)

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
