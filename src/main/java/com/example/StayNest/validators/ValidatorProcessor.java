package com.example.StayNest.validators;

import com.example.StayNest.models.Booking;

public class ValidatorProcessor {

    public static RequestValidator getChainOfValidators(Booking booking) {
//        RequestValidator validateBooking = new ValidateBooking(RequestValidator.booking);
//        RequestValidator dateValidator = new DateValidator(RequestValidator.date);
//        RequestValidator availabilityValidator = new AvailabilityValidator(RequestValidator.available);
//        RequestValidator conflictValidator = new ConflictValidator(RequestValidator.conflict);
//        RequestValidator authorizationValidator = new AuthorizationValidator(RequestValidator.auth);

        Validate validate = new Validate(true, true, true, true, true);

        RequestValidator bookingValidator = new BookingValidator(booking, validate);
        RequestValidator dateValidator = new DateValidator();
        RequestValidator availabilityValidator = new AvailabilityValidator(booking, validate);
        RequestValidator conflictValidator = new ConflictValidator();
        RequestValidator authorizationValidator = new AuthorizationValidator();


//        bookingValidator.setNextValidator(dateValidator);
//        dateValidator.setNextValidator(availabilityValidator);
//        availabilityValidator.setNextValidator(conflictValidator);
//        conflictValidator.setNextValidator(authorizationValidator);

        return bookingValidator;
    }


}
