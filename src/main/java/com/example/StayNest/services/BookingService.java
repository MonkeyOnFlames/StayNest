package com.example.StayNest.services;

import com.example.StayNest.models.Booking;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

private final BookingRepository bookingRepository;
private final UserRepository userRepository;
private final ListingRepository listingRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Booking booking) {
        if (booking.getId() == null || booking.getId().isEmpty()) {
            throw new IllegalArgumentException("Booking id cannot be empty");
        }
        if (booking.getDescription() == null || booking.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Booking Description can not be empty");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
//PATCH
    public Booking patchBooking(String userid, String bookingId, @RequestBody String listingId, boolean available){
        //Här kollar vi om rätt user finns för rätt bokning genom id
        User user = userRepository.findById(userid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Här kollar vi om vi hittar rätt bokning genom id
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        // Här kollar vi om rätt listing finns genom id
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing not found"));

        // Available är boolean: true: tillgånglig. false: uthyrd.
        booking.setAvailable(false);
        //Är det här rätt?!
patchBooking(userid, bookingId, listingId,available).
        setAvailable(patchBooking(userid, bookingId, listingId,available).isAvailable());

        bookingRepository.save(booking);
//ska det finnas fler getter och setters här?
        booking.setTotalAmount(booking.getTotalAmount());
        booking.setCreatedAt(new Date());

        //Varför är den här fel?!
        return ResponseEntity.ok(Booking);
    }

}
