package com.example.StayNest.helpClasses.validatorChain;

import com.example.StayNest.models.Booking;

import java.time.LocalDate;

public class ValidateDate {

    public boolean validateDate (Booking booking){
        return (booking.getStartDate().isBefore(booking.getEndDate()) || booking.getStartDate() == booking.getEndDate()) &&
                booking.getStartDate().isAfter(LocalDate.now());
    }
}
