package com.example.StayNest.validators;

public class AuthorizationValidator extends RequestValidator {
    @Override
    protected void write(String message) {
        System.out.println("BOOKING: " + message);
    }
}