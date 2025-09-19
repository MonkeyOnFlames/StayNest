package com.example.StayNest.validators;

import com.example.StayNest.helpClasses.validatorChain.ValidateConflict;
import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.BookingRepository;

public class ConflictValidator extends RequestValidator {

    private final Booking booking;
    private final Validate validate;
    private final BookingRepository bookingRepository;

    public ConflictValidator(Booking booking, Validate validate, BookingRepository bookingRepository) {
        this.booking = booking;
        this.validate = validate;
        this.bookingRepository = bookingRepository;
    }


    @Override
    public void validationHandler () {
        ValidateConflict validateConflict = new ValidateConflict(bookingRepository);

        validate.setConflict(validateConflict.validateConflict(booking));

        if (validate.isConflict()) {
            super.validationHandler();
        } else {
            throw new IllegalArgumentException("There is already another booking on that date for the chosen listing");
        }
    }

}
