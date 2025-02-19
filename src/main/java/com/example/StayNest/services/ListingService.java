package com.example.StayNest.services;

import com.example.StayNest.models.Listing;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository repository, UserRepository userRepository) {
        this.listingRepository = repository;
        this.userRepository = userRepository;
    }

    public Listing createListing(@Valid Listing listing) {

        validateListing(listing);

        return listingRepository.save(listing);
    }

    public Listing getListingsById(String id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id " + id));
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing updateListing(String id, Listing listing) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id " + id));

        //uppdatera endast icke null fält
        if (listing.getName() != null){
            existingListing.setName(listing.getName());
        }
        if (listing.getLocation() != null){
            existingListing.setLocation(listing.getLocation());
        }
        if (listing.getDescription() != null){
            existingListing.setDescription(listing.getDescription());
        }
        if (listing.getPrice() != null){
            existingListing.setPrice(listing.getPrice());
        }
        if (listing.getListingPolicy() != null){
            existingListing.setListingPolicy(listing.getListingPolicy());
        }
        if (listing.getEnvironment() != null){
            existingListing.setEnvironment(listing.getEnvironment());
        }
        if (listing.getRestrictions() != null){
            existingListing.setRestrictions(listing.getRestrictions());
        }
        if (listing.getPictureURLs() != null){
            existingListing.setPictureURLs(listing.getPictureURLs());
        }
        if (listing.getAvailabilities() != null){
            existingListing.setAvailabilities(listing.getAvailabilities());
        }

        return listingRepository.save(existingListing);
    }

    public void deleteListing(String id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id: " + id));

        listingRepository.delete(listing);
    }

    public List<Listing> getListingsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price values cannot be negative");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("MinPrice cannot be greater than maxPrice");
        }
        List<Listing> listings = listingRepository.findByPriceBetween(minPrice, maxPrice);
        if (listings.isEmpty()) {
            throw new NoSuchElementException("No listings found within price range: " + minPrice + " - " + maxPrice);
        }
        return listings;
    }

    private void validateListing(Listing listing){
        if (listing.getName() == null || listing.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
        if (listing.getLocation() == null || listing.getLocation().trim().isEmpty()){
            throw new IllegalArgumentException("Location cannot be empty or null");
        }
        if (listing.getDescription() == null || listing.getDescription().trim().isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty or null");
        }
        if (listing.getPrice() < 0){
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
        if (listing.getListingTypes() == null){
            throw new IllegalArgumentException("Type cannot be less than 0");
        }
        if (listing.getListingPolicy() == null || listing.getListingPolicy().trim().isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty or null");
        }
        if (listing.getPictureURLs() == null){
            throw new IllegalArgumentException("You need at least one picture");
        }
        if (listing.getAvailabilities() == null){
            throw new IllegalArgumentException("Availability cannot be empty or null");
        }
    }

}
