package com.abs.restaurant.app.security.repository;

import com.abs.restaurant.app.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}
