package com.example.StayNest.controllers;

import com.example.StayNest.dto.ListingResponseDTO;
import com.example.StayNest.dto.ListingResponseGetAll;
import com.example.StayNest.models.Environment;
import com.example.StayNest.models.Listing;
import com.example.StayNest.services.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<ListingResponseDTO> createListing(@Valid @RequestBody Listing listing) {
        ListingResponseDTO createdListing = listingService.createListing(listing);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDTO> getListingById(@PathVariable String id) {
        ListingResponseDTO listing = listingService.getListingsById(id);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ListingResponseGetAll>> getAllListings() {
        List<ListingResponseGetAll> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'ADMIN')")
    public ResponseEntity<ListingResponseDTO> updateListing(@Valid @PathVariable String id, @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.updateListing(id, listing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'ADMIN')")
    public ResponseEntity<Void> deleteListing(@PathVariable String id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/price")
    public ResponseEntity<List<ListingResponseGetAll>> getListingByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<ListingResponseGetAll> listings = listingService.getListingsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/environments")
    public ResponseEntity<List<ListingResponseGetAll>> getListingsByEnvironments() {
        List<ListingResponseGetAll> listings = listingService.getListingsWithEnvironments();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/{environment}/specific")
    public ResponseEntity<List<ListingResponseGetAll>> getListingsBySpecificEnvironments(@PathVariable Environment environment) {
        List<ListingResponseGetAll> listings = listingService.getListingsWithSpecificEnvironment(environment);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }
}
