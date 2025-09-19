package com.example.StayNest.validators;

import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.BookingRepository;

public class ValidatorProcessor {

    public static RequestValidator getChainOfValidators(Booking booking, BookingRepository bookingRepository, ListingRepository listingRepository) {
//        RequestValidator validateBooking = new ValidateBooking(RequestValidator.booking);
//        RequestValidator dateValidator = new DateValidator(RequestValidator.date);
//        RequestValidator availabilityValidator = new AvailabilityValidator(RequestValidator.available);
//        RequestValidator conflictValidator = new ConflictValidator(RequestValidator.conflict);
//        RequestValidator authorizationValidator = new AuthorizationValidator(RequestValidator.auth);

        Validate validate = new Validate(true, true, true, true, true);

        RequestValidator bookingValidator = new BookingValidator(booking, validate);
        RequestValidator dateValidator = new DateValidator(booking, validate);
        RequestValidator conflictValidator = new ConflictValidator(booking, validate, bookingRepository);
        RequestValidator availabilityValidator = new AvailabilityValidator(booking, validate, listingRepository);
        RequestValidator authorizationValidator = new AuthorizationValidator();


        bookingValidator.setNextValidator(dateValidator);
        dateValidator.setNextValidator(availabilityValidator);
        availabilityValidator.setNextValidator(conflictValidator);
//        conflictValidator.setNextValidator(authorizationValidator);

        return bookingValidator;
    }


}
