package com.example.StayNest.controllers;


import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
private final BookingRepository bookingRepository;
    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking){
        Booking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
//se vilken user har gjort bokningen genom id

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById (@PathVariable String id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found"));
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable String id){
        if (!bookingRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found");
        }

        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();



}
