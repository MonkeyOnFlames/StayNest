package com.example.StayNest.services;

import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        if (Booking.getName() == null || booking.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (booking.getDescription() == null || booking.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

}
