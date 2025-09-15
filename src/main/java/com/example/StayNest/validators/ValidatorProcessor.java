package com.example.StayNest.validators;

import com.example.StayNest.models.Booking;

public class ValidatorProcessor {

    private static RequestValidator getChainOfValidators(Booking booking) {
//        RequestValidator validateBooking = new ValidateBooking(RequestValidator.booking);
//        RequestValidator dateValidator = new DateValidator(RequestValidator.date);
//        RequestValidator availabilityValidator = new AvailabilityValidator(RequestValidator.available);
//        RequestValidator conflictValidator = new ConflictValidator(RequestValidator.conflict);
//        RequestValidator authorizationValidator = new AuthorizationValidator(RequestValidator.auth);

        RequestValidator validateBooking = new ValidateBooking(booking);
        RequestValidator dateValidator = new DateValidator();
        RequestValidator availabilityValidator = new AvailabilityValidator();
        RequestValidator conflictValidator = new ConflictValidator();
        RequestValidator authorizationValidator = new AuthorizationValidator();


        validateBooking.setNextValidator(dateValidator);
        dateValidator.setNextValidator(availabilityValidator);
        availabilityValidator.setNextValidator(conflictValidator);
        conflictValidator.setNextValidator(authorizationValidator);

        return validateBooking;
    }


}
