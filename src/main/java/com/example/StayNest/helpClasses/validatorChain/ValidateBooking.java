package com.example.StayNest.helpClasses.validatorChain;

import com.example.StayNest.models.Booking;

public class ValidateBooking {

    private Booking booking;

    public boolean validateBooking(Booking booking) {
        return booking.getListing() != null &&
                booking.getUser() != null &&
                booking.getStartDate() != null &&
                booking.getEndDate() != null;

    }
}