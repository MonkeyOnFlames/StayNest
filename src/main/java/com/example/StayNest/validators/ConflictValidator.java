package com.example.StayNest.validators;

public class ConflictValidator extends RequestValidator {
    @Override
    protected void write(String message) {
        System.out.println("BOOKING: " + message);}
}
