package com.example.StayNest.services;

import com.example.StayNest.dto.ListingResponseDTO;
import com.example.StayNest.dto.ListingResponseGetAll;
import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.Role;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository repository, UserRepository userRepository) {
        this.listingRepository = repository;
        this.userRepository = userRepository;
    }

    public ListingResponseDTO createListing(@Valid Listing listing) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRoles().equals(Set.of(Role.USER))) {
            user.setRoles(Set.of(Role.LANDLORD));
        }
        listing.setUser(user);

        validateListing(listing);

        Listing savedListing = listingRepository.save(listing);

        return convertToListingResponseDTO(savedListing);
    }

    public ListingResponseDTO getListingsById(String id) {

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing with id " + id + " not found"));


        return convertToListingResponseDTO(listing);
    }

    public List<ListingResponseGetAll> getAllListings() {
        List<Listing> listings =  listingRepository.findAll();

        return listings.stream()
                .map(this::convertToListingResponseGetAll)
                .collect(Collectors.toList());
    }

    public Listing updateListing(String id, Listing listing) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id " + id));

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
        if (listing.getListingTypes() != null){
            existingListing.setListingTypes(listing.getListingTypes());
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
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id: " + id));

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
            throw new ResourceNotFoundException("No listings found within price range: " + minPrice + " - " + maxPrice);
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
            throw new IllegalArgumentException("Type cannot be null");
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

    private ListingResponseDTO convertToListingResponseDTO(Listing listing){
        ListingResponseDTO listingResponseDTO = new ListingResponseDTO();

        listingResponseDTO.setId(listing.getId());
        listingResponseDTO.setUserId(listing.getUser().getId());
        listingResponseDTO.setFirstName(listing.getUser().getFirstName());
        listingResponseDTO.setName(listing.getName());
        listingResponseDTO.setLocation(listing.getLocation());
        listingResponseDTO.setDescription(listing.getDescription());
        listingResponseDTO.setPrice(listing.getPrice());
        listingResponseDTO.setListingTypes(listing.getListingTypes());
        listingResponseDTO.setListingPolicy(listing.getListingPolicy());
        listingResponseDTO.setEnvironment(listing.getEnvironment());
        listingResponseDTO.setRestrictions(listing.getRestrictions());
        listingResponseDTO.setPictureURLs(listing.getPictureURLs());
        listingResponseDTO.setAvailabilities(listing.getAvailabilities());
        listingResponseDTO.setCreatedAt(listing.getCreatedAt());

        return listingResponseDTO;
    }

    private ListingResponseGetAll convertToListingResponseGetAll(Listing listing){
        ListingResponseGetAll listingResponseGetAll = new ListingResponseGetAll();
        listingResponseGetAll.setId(listing.getId());
        listingResponseGetAll.setFirstName(listing.getUser().getFirstName());
        listingResponseGetAll.setName(listing.getName());
        listingResponseGetAll.setLocation(listing.getLocation());
        listingResponseGetAll.setPrice(listing.getPrice());
        listingResponseGetAll.setListingTypes(listing.getListingTypes());
        listingResponseGetAll.setEnvironment(listing.getEnvironment());
        listingResponseGetAll.setPictureURLs(listing.getPictureURLs());
        listingResponseGetAll.setAvailabilities(listing.getAvailabilities());

        return listingResponseGetAll;
    }
}
