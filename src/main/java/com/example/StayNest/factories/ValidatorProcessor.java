package com.example.StayNest.factories;

public class ValidatorProcessor {
    private static RequestValidator getChainOfValidators() {
        RequestValidator dateValidator = new DateValidator(Logger.ERROR);
        RequestValidator validateBooking = new ValidateBooking(Logger.DEBUG);
        RequestValidator authorizationValidator = new AuthorizationValidator(Logger.INFO);
        RequestValidator availabilityValidator = new AvailabilityValidator(Logger.INFO);
        RequestValidator conflictValidator = new ConflictValidator(Logger.INFO);

        dateValidator.setNext(validateBooking);
        validateBooking.setNext(authorizationValidator);
        authorizationValidator.setNext(availabilityValidator);
        availabilityValidator.setNext(conflictValidator);

        return dateValidator;
    }
}
