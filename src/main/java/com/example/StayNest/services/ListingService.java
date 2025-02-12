package com.example.StayNest.services;

import com.example.StayNest.models.Listing;
import com.example.StayNest.repositories.ListingRepository;

public class ListingService {
    private final ListingRepository listingRepository;
    private final UserService userService;

    public ListingService(ListingRepository repository, UserService userService) {
        this.listingRepository = repository;
        this.userService = userService;
    }

    public Listing createListing(Listing listing) {
        validateListing(listing);

        return listingRepository.save(listing);
    }

    private void validateListing(Listing listing){
        if (listing.getName() == null || listing.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Product name cannot be empty or null");
        }
        if (listing.getPrice() < 0){
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
        if (listing.getStockQuantity() < 0){
            throw new IllegalArgumentException("Product stockQuantity cannot be less than 0");
        }
    }

}
