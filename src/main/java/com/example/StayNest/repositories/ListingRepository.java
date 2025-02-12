package com.example.StayNest.repositories;

import com.example.StayNest.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<User, String> {
}
