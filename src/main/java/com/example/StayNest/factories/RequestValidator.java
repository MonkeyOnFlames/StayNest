package com.example.StayNest.factories;

public abstract class RequestValidator {
//    public static int DATE = 1;
//    public static int BOOKING = 2;
//    public static int AUTH = 3;
//    public static int AVAILABLE = 4;
//    public static int CONFLICT = 5;

    public static boolean DATE;
    public static boolean BOOKING;
    public static boolean AUTH;
    public static boolean AVAILABLE;
    public static boolean CONFLICT;

//    protected int validate;
    protected boolean validate;
    protected RequestValidator nextValidator;

    public void setNextValidator(RequestValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

//    public void logMessage(int validate, String message) {
//        if (this.validate == validate) {
//            write(message);
//        } else if (nextValidator != null) {
//            nextValidator.logMessage(validate, message);
//        }
//    }

    public void logMessage(boolean validate, String message) {
        if (this.validate == validate) {
            write(message);
        } else if (nextValidator != null) {
            nextValidator.logMessage(validate, message);
        }
    }

    abstract protected void write(String message);
}
