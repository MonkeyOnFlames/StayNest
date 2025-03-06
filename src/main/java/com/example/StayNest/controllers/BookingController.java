package com.example.StayNest.controllers;


import com.example.StayNest.dto.BookingRequestDTO;
import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('USER', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<BookingResponseDTO> createNewBooking(@RequestBody BookingRequestDTO booking){
        BookingResponseDTO createdBooking = bookingService.createNewBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody Booking booking){
        BookingResponseDTO createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

//Hitta en bokning genom id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<BookingResponseDTO> getBookingById (@PathVariable String id){
        BookingResponseDTO booking = bookingService.getBookingsById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<Booking> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }



}
