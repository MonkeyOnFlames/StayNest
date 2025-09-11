package com.example.StayNest.factories;

public abstract class RequestValidator {
    protected RequestValidator nextValidator;

    public void setNext(RequestValidator nextValidator) {
        this.nextValidator = nextValidator;
    }
}
