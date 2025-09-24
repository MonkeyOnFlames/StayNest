package com.example.StayNest.validators;

import com.example.StayNest.helpClasses.validatorChain.ValidateAuthorization;
import com.example.StayNest.services.UserService;

public class AuthorizationValidator extends RequestValidator {

    private final Validate validate;
    private final UserService userService;

    public AuthorizationValidator(Validate validate, UserService userService) {
        this.validate = validate;
        this.userService = userService;
    }



    @Override
    public void validationHandler () {
        ValidateAuthorization validateAuthorization = new ValidateAuthorization(userService);
        validate.setAuth(validateAuthorization.validateAuthorization());

        if (validate.isAuth()) {
            super.validationHandler();
        } else {
            throw new IllegalArgumentException("You need to be logged in to make a booking");
        }
    }
}