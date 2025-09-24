package com.example.StayNest.validators;

import com.example.StayNest.helpClasses.validatorChain.ValidateAvailability;
import com.example.StayNest.models.Booking;
import com.example.StayNest.repositories.ListingRepository;

public class AvailabilityValidator extends RequestValidator {
    private final Booking booking;
    private final Validate validate;
    private final ListingRepository listingRepository;

    public AvailabilityValidator(Booking booking, Validate validate, ListingRepository listingRepository) {
        this.booking = booking;
        this.validate = validate;
        this.listingRepository = listingRepository;
    }

    @Override
    public void validationHandler () {
        ValidateAvailability validateAvailability = new ValidateAvailability(listingRepository);
        validate.setAvailable(validateAvailability.validateAvailability(booking));

        if (validate.isAvailable()) {
            super.validationHandler();
        } else {
            throw new IllegalArgumentException("The selected dates are not available for booking");
        }
    }
}
