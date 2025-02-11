package com.example.StayNest.repositories;

import com.example.StayNest.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByName(String name);
}
