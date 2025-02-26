package com.example.StayNest.repositories;

import com.example.StayNest.models.Role;
import com.example.StayNest.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsernameAndRolesContaining(String username, Role role);
}
