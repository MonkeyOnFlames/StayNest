package com.example.StayNest.repositories;

import com.example.StayNest.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListingRepository extends MongoRepository<Listing, String> {
    Listing findByListingId(String listingId);
    List<Listing> findByUserId(String userId);
    List<Listing> findByPriceBetween(double minPrice, double maxPrice);
}
