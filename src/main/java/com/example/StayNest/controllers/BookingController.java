package com.example.StayNest.controllers;


import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
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

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity.ok(bookings);
    }
//se vilken user har gjort bokningen genom id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBooking(@PathVariable String userId){
        List<Booking> bookings = bookingService.getUserBooking(userId);
        return new ResponseEntity.ok(bookings);
    }


}
