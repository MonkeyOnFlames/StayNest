package com.example.StayNest.helpClasses.validatorChain;

import com.example.StayNest.models.User;
import com.example.StayNest.services.UserService;

public class ValidateAuthorization {

    private final UserService userService;

    public ValidateAuthorization(UserService userService) {
        this.userService = userService;
    }

    public boolean validateAuthorization() {
        User loggedInUser = userService.getLoggedInUser();

        return loggedInUser != null;
    }
}
