package com.example.StayNest.validators;

public abstract class RequestValidator {
    public static int BOOKING = 1;
    public static int DATE = 2;
    public static int AVAILABLE = 3;
    public static int CONFLICT = 4;
    public static int AUTH = 5;

    protected int validate;

    protected RequestValidator nextValidator;

    public void setNextValidator(RequestValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public void logMessage(int validate, String message) {
        if (this.validate == validate) {
            write(message);
        } else if (nextValidator != null) {
            nextValidator.logMessage(validate, message);
        }
    }

    abstract protected void write(String message);
}
