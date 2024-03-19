package com.abs.restaurant.app.security.repository;

import com.abs.restaurant.app.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("FROM User u WHERE u.email = :email or u.dni = :dni")
    Optional<User> findByEmailOrDni(String email, String dni);
}
