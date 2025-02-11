package com.example.StayNest.controllers;


import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking){
        Booking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Booking>> getBookingByName(@PathVariable String name){
        List<Booking> bookings = bookingService.getBookingByname(name);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


}
