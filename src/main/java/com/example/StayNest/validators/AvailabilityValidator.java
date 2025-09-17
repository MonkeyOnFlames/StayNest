package com.example.StayNest.validators;

import com.example.StayNest.helpClasses.validatorChain.ValidateBooking;
import com.example.StayNest.models.Booking;

public class AvailabilityValidator extends RequestValidator {
    private final Booking booking;
    private final Validate validate;

    public AvailabilityValidator(Booking booking, Validate validate) {
        this.booking = booking;
        this.validate = validate;
    }

    ValidateBooking validateBooking = new ValidateBooking();

    @Override
    public void validationHandler () {
        validate.setBooking(validateBooking.validateBooking(booking));

        if (validate.isBooking()) {
            super.validationHandler();
        } else {
            throw new IllegalArgumentException("All required fields must be filled");
        }
    }
}
