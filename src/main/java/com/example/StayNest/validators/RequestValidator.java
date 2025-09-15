package com.example.StayNest.validators;

public abstract class RequestValidator {
//    public static int BOOKING = 1;
//    public static int DATE = 2;
//    public static int AVAILABLE = 3;
//    public static int CONFLICT = 4;
//    public static int AUTH = 5;

//    private static boolean BOOKING;
//    public static boolean DATE;
//    public static boolean AVAILABLE;
//    public static boolean CONFLICT;
//    public static boolean AUTH;


//    protected int validate;

//    protected boolean validate;

    protected RequestValidator nextValidator;

//    public static boolean isBOOKING() {
//        return BOOKING;
//    }

//    public static void setBOOKING(boolean BOOKING) {
//        RequestValidator.BOOKING = BOOKING;
//    }

    public void setNextValidator(RequestValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

//    public void validateMessage(int validate, String message) {
//        if (this.validate == validate) {
//            write(message);
//        } else if (nextValidator != null) {
//            nextValidator.validateMessage(validate, message);
//        }
//    }

    public void validationHandler(Validate validate/*, String message*/) {
        if (nextValidator != null) {
            nextValidator.validationHandler(validate/*, message*/);
        } /*else {
            write(message);
        }*/
    }

//    protected abstract void validate(Validate validate);

//    public void validateMessage(boolean validate, String message) {
//        if (this.validate == validate) {
//            write(message);
//        } else if (nextValidator != null) {
//            nextValidator.validateMessage(validate, message);
//        }
//    }

//    abstract protected void write(String message);
}
