package com.example.StayNest.repositories;

import com.example.StayNest.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
