package com.example.StayNest.validators;

public abstract class RequestValidator {

    //got help from this site to make a chain of responsibility:
    //https://dev.to/syridit118/understanding-the-chain-of-responsibility-design-pattern-in-backend-development-p2f
    protected RequestValidator nextValidator;

    public void setNextValidator(RequestValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public void validationHandler() {
        if (nextValidator != null) {
            nextValidator.validationHandler();
        } /*else {
            write(message);
        }*/
    }

}
