package com.example.StayNest.services;

import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

private final BookingRepository bookingRepository;

    public Booking CreatedBooking(Booking booking){
    validateBooking(booking);

    return bookingRepository.save(booking);
}
}
