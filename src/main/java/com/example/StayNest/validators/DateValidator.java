package com.example.StayNest.validators;

import com.example.StayNest.helpClasses.validatorChain.ValidateDate;
import com.example.StayNest.models.Booking;

public class DateValidator extends RequestValidator {

    private final Booking booking;
    private final Validate validate;

    public DateValidator(Booking booking, Validate validate) {
        this.booking = booking;
        this.validate = validate;
    }

    ValidateDate validateDate = new ValidateDate();

    @Override
    public void validationHandler () {
        validate.setDate(validateDate.validateDate(booking));

        if (validate.isDate()) {
            super.validationHandler();
        } else {
            throw new IllegalArgumentException("That is not a valid booking span");
        }
    }

}
