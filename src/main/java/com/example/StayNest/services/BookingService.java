package com.example.StayNest.services;

import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingService {

private final BookingRepository bookingRepository;
private final UserRepository userRepository;
private final ListingRepository listingRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ListingRepository listingRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public Booking createBooking(Booking booking) {
        if (booking.getId() == null || booking.getId().isEmpty()) {
            throw new IllegalArgumentException("Booking id cannot be empty");
        }
        if (booking.getListingId() == null || booking.getListingId().isEmpty()) {
            throw new IllegalArgumentException("Booking Description can not be empty");
        }


        SimpleDateFormat startDate = new SimpleDateFormat("YYYY-MM-DD");
        SimpleDateFormat endDate = new SimpleDateFormat("YYYY-MM-DD");

// create new booking, creation time, user that made the booking, witch listing is booked,
// total amount, start and end date
        Booking newBooking = new Booking();
        newBooking.setCreatedAt(new Date());
        newBooking.setUserId(newBooking.getUserId());
        newBooking.setListingId(newBooking.getListingId());
        newBooking.setTotalAmount(newBooking.getTotalAmount());
        newBooking.setStartDate(new Date());
        newBooking.setEndDate(new Date());

        return bookingRepository.save(booking);
    }



//PATCH
   /* public Booking updateBooking(String userid, String bookingId, @RequestBody String listingId, boolean available){
        //Här kollar vi om rätt user finns för rätt bokning genom id
        User user = userRepository.findById(userid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Här kollar vi om vi hittar rätt bokning genom id
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        // Här kollar vi om rätt listing finns genom id
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing not found"));


        booking.setAvailable(false);
        //Är det här rätt?!
updateBooking(userid, bookingId, listingId,available).
        setAvailable(updateBooking(userid, bookingId, listingId,available).isAvailable());

        bookingRepository.save(booking);
//ska det finnas fler getter och setters här?
        booking.setTotalAmount(booking.getTotalAmount());
        booking.setCreatedAt(new Date());

        //Varför är den här fel?!
        return ResponseEntity.ok(Booking);
    }

    */

}
