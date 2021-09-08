package com.myTrade.utility.validators;

import com.myTrade.utility.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class RegistrationRequestValidator implements Predicate<RegistrationRequest> {

    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;
    private BirthDayValidator birthDayValidator;
    private UserNameValidator userNameValidator;

    @Autowired
    public RegistrationRequestValidator(EmailValidator emailValidator, PasswordValidator passwordValidator, BirthDayValidator birthDayValidator, UserNameValidator userNameValidator) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.birthDayValidator = birthDayValidator;
        this.userNameValidator = userNameValidator;
    }

    @Override
    public boolean test(RegistrationRequest registrationRequest) {
        if (userNameValidator.test(registrationRequest.getUserName())
                && passwordValidator.test(registrationRequest.getPassword())
                && emailValidator.test(registrationRequest.getEmail())
                && birthDayValidator.test(registrationRequest.getBirthDate())) {
            return true;
        }
        return true;
    }
}
