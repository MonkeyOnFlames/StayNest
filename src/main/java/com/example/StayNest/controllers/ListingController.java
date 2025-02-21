package com.example.StayNest.controllers;

import com.example.StayNest.dto.ListingResponseGetAll;
import com.example.StayNest.dto.ListingResponseDTO;
import com.example.StayNest.models.Listing;
import com.example.StayNest.services.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ListingResponseDTO> createListing(@RequestBody Listing listing) {
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
    public ResponseEntity<Listing> updateListing(@PathVariable String id, @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.updateListing(id, listing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable String id) {
        listingService.deleteListing(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/price")
    public ResponseEntity<List<Listing>> getListingByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Listing> listings = listingService.getListingsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }



}
