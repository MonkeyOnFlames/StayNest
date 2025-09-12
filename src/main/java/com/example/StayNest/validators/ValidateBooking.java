package com.example.StayNest.validators;

public class ValidateBooking extends RequestValidator{
    public ValidateBooking(int validate) {
        this.validate = validate;
    }

    @Override
    protected void write(String message) {
        System.out.println("BOOKING: " + message);
    }
}
