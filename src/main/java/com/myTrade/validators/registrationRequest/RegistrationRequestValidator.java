package com.myTrade.validators.registrationRequest;

import com.myTrade.utility.pojo.RegistrationRequest;

import java.util.function.Predicate;

public class RegistrationRequestValidator implements Predicate<RegistrationRequest> {
    EmailValidator emailValidator = new EmailValidator();
    PasswordValidator passwordValidator = new PasswordValidator();
    UsernameValidator usernameValidator = new UsernameValidator();

    @Override
    public boolean test(RegistrationRequest registrationRequest) {
        Boolean isUsernameValid = usernameValidator.test(registrationRequest.getUsername());
        Boolean isEmailValid = emailValidator.test(registrationRequest.getEmail());
        Boolean isPasswordValid = passwordValidator.test(registrationRequest.getPassword());
        return isUsernameValid && isEmailValid && isPasswordValid;
    }
}
