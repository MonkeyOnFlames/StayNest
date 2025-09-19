package com.example.StayNest.validators;

import com.example.StayNest.helpClasses.validatorChain.ValidateAvailability;

public class AuthorizationValidator extends RequestValidator {
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