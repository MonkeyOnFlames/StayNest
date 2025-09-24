package com.example.StayNest.helpClasses.validatorChain;

import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.repositories.BookingRepository;

import java.util.List;

public class ValidateConflict {

    private final BookingRepository bookingRepository;

    public ValidateConflict(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    public boolean validateConflict(Booking booking) {

        Listing listing = booking.getListing();

        List<Booking> existingBookings = bookingRepository.findByListingIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                listing.getId(), booking.getEndDate(), booking.getStartDate());

        return existingBookings == null || existingBookings.isEmpty();

    }
}
