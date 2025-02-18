package com.example.StayNest.controllers;

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
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        Listing createdListing = listingService.createListing(listing);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable String id) {
        Listing listing = listingService.getListingsById(id);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Listing> patchListing(@PathVariable String id, @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.patchListing(id, listing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable String id) {
        listingService.deleteListing(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<Listing>> getListingByUserId(@PathVariable String userId) {
//        List<Listing> listings = listingService.getListingsByUserId(userId);
//        return new ResponseEntity<>(listings, HttpStatus.OK);
//    }

    @GetMapping("/price")
    public ResponseEntity<List<Listing>> getListingByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Listing> listings = listingService.getListingsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }



}
