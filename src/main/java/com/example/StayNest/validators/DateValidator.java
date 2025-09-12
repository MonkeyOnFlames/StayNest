package com.example.StayNest.validators;

public class DateValidator extends RequestValidator {
    public DateValidator(boolean validate) {
        this.validate = validate;
    }

    @Override
    protected void write(String message) {
        System.out.println("BOOKING: " + message);
    }
}
